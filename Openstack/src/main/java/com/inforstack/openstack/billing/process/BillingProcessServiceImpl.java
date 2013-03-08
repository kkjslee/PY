package com.inforstack.openstack.billing.process;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.basic.BasicDaoImpl.CursorResult;
import com.inforstack.openstack.billing.invoice.InvoiceCount;
import com.inforstack.openstack.billing.process.conf.BillingProcessConfiguration;
import com.inforstack.openstack.billing.process.conf.BillingProcessConfigurationService;
import com.inforstack.openstack.billing.process.result.BillingProcessResult;
import com.inforstack.openstack.billing.process.result.BillingProcessResultService;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.order.OrderService;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.NumberUtil;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.SecurityUtils;

@Service("billingProcessService")
@Transactional
public class BillingProcessServiceImpl implements BillingProcessService {

	private static final Logger log = new Logger(BillingProcessServiceImpl.class);
	private static final ReentrantReadWriteLock scheduleLock = new ReentrantReadWriteLock();
	private static final ReentrantReadWriteLock executeLock = new ReentrantReadWriteLock();
	private static final Lock confLock = new ReentrantLock();
	private static final Set<Integer> runningConf = new HashSet<Integer>();
	
	@Autowired
	private BillingProcessDao billingProcessDao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private BillingProcessConfigurationService billingProcessConfigurationService;
	@Autowired
	private BillingProcessResultService billingProcessResultService;
	
	@Override
	public BillingProcess createBillingProcess(BillingProcess billingProcess) {
		log.debug("Create billing Process");
		billingProcessDao.persist(billingProcess);
		log.debug("Create billing process successfully");
		
		return billingProcess;
	}

	@Override
	public BillingProcess createBillingProcess(
			BillingProcessConfiguration config, Date startTime, User user) {
		log.debug("Create billing process");
		BillingProcess bp =  new BillingProcess();
		bp.setBillingProcessConfiguration(config);
		bp.setStartTime(startTime);
		bp.setRetry(0);
		bp.setStatus(Constants.BILLINGPROCESS_STATUS_NEW);
		if(user != null){
			bp.setUser(user);
		}
		billingProcessDao.persist(bp);
		log.debug("Create billing process failed");
		
		return bp;
	}
	
	@Override
	public BillingProcess findBillingProcessById(int billingProcessId) {
		log.debug("Find billing process by id : " + billingProcessId);
		BillingProcess bp = billingProcessDao.findById(billingProcessId);
		if(bp == null){
			log.debug("Find billing process failed");
		}else{
			log.debug("Find billing process successfully");
		}
		
		return bp;
	}
	
	@Override
	public BillingProcessResult runBillingProcess(BillingProcessConfiguration conf, Boolean autoPay) {
		return this.runBillingProcess(conf, null, autoPay);
	}
	
	@Override
	public BillingProcessResult runBillingProcess(Integer tenantId, Boolean autoPay) {
		return this.runBillingProcess(null, tenantId, autoPay);
	}
	
	@Override
	public BillingProcessResult runBillingProcess(BillingProcessConfiguration conf, Integer tenantId, Boolean autoPay) {
		log.debug("Running billing process for billing process configuration : " + 
				conf==null? null:conf.getId() + ", tenant : " +tenantId);
		confLock.lock();
		try{
			if(conf != null && runningConf.contains(conf.getId())){
				return null;
			}
			
			if(conf != null){
				runningConf.add(conf.getId());
			}
		}finally{
			confLock.unlock();
		}
		
		Integer confId = null;
		if(conf != null){
			confId = conf.getId();
		}
		BillingProcessService self = (BillingProcessService)OpenstackUtil.getBean("billingProcessService");
		BillingProcessResult bpr = self.createBillingProcessResult(confId);
		BillingProcess bp = bpr.getBillingProcess();
		
		Integer currentOrder = null;
		try{
			CursorResult<Order> orders = orderService.findAll(tenantId, Constants.ORDER_STATUS_ACTIVE);
			while(orders.hasNext()){
				Order order = orders.getNext();
				currentOrder = order.getId();
				
				bpr = self.processOrder(currentOrder, autoPay, bpr.getId());
			}
			orders.close();
			
			if( conf != null){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(conf.getNextBillingDate());
				calendar.add(conf.getPeriodType(), conf.getPeriodQuantity());
				conf.setNextBillingDate(calendar.getTime());
			}
			
			bp.setEndTime(new Date());
			bp.setStatus(Constants.BILLINGPROCESS_STATUS_SUCCESS);
			log.debug("Running billing process finished");
			
			return bpr;
		}catch(RuntimeException re){
			int c = bpr.getInvoiceTotal().compareTo(BigDecimal.ZERO);
			if(c == 0){
				bp.setEndTime(new Date());
				bp.setStatus(Constants.BILLINGPROCESS_STATUS_FAILED);
				log.error("Running billing process failed");
			}else if(c > 0){
				bp.setEndTime(new Date());
				bp.setStatus(Constants.BILLINGPROCESS_STATUS_PART_SUCCESS);
				log.error("Running billing process partly successfully");
			}else{
				log.fetal("Invoice total amount is less than 0 for billing process: " + bp.getId());
			}
			log.error("Error occured while billing processing order : " + currentOrder, re);
		}finally{
			runningConf.remove(conf);
		}
		
		return bpr;
	}
	
	public BillingProcessResult runBillingProcessForOrder(int orderId){
		return this.runBillingProcessForOrder(orderId, null);
	}
	
	@Override
	public BillingProcessResult runBillingProcessForOrder(int orderId, Boolean autoPay) {
		log.debug("Running billing process for order : " + orderId);
		BillingProcessService self = (BillingProcessService)OpenstackUtil.getBean("billingProcessService");
		BillingProcessResult bpr = self.createBillingProcessResult(null);
		BillingProcess bp = bpr.getBillingProcess();
		
		try{
			bpr = self.processOrder(orderId, autoPay, bpr.getId());
			bp.setEndTime(new Date());
			bp.setStatus(Constants.BILLINGPROCESS_STATUS_SUCCESS);
			log.debug("Running billing process finished");
		}catch(RuntimeException re){
			bp.setEndTime(new Date());
			bp.setStatus(Constants.BILLINGPROCESS_STATUS_FAILED);
			log.debug("Running billing process failed");
		}
		
		return bpr;
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public BillingProcessResult createBillingProcessResult(Integer billingProcessConfId){
		BillingProcessConfiguration conf = null;
		
		if(billingProcessConfId != null){
			conf = billingProcessConfigurationService.findBillingProcessConfigurationById(billingProcessConfId);
			if(conf == null){
				log.error("No billing process configuration found by id : " + billingProcessConfId);
				throw new ApplicationRuntimeException("No billing process configuration found");
			}
		}
		
		BillingProcess bp = this.createBillingProcess(conf, new Date(), SecurityUtils.getUser());
		BillingProcessResult bpr = billingProcessResultService.createBillingProcessResult(bp);
		return bpr;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public BillingProcessResult processOrder(int orderId, Boolean autoPay, int billingProcessResultId){
		BillingProcessResult bpr = null;
		Lock lock = null;
		try{
			bpr = billingProcessResultService.findBillingProcessResult(billingProcessResultId);
			if(bpr == null) return null;
			
			Order order = orderService.findOrderById(orderId);
			if(order == null) return bpr;
			
			if(new Integer(Constants.ORDER_STATUS_NEW).equals(order.getStatus()) 
					|| new Integer(Constants.ORDER_STATUS_ACTIVE).equals(order.getStatus())){
				
				Date billingDate = null;
				BillingProcess bp = bpr.getBillingProcess();
				if(bp != null && bp.getBillingProcessConfiguration() != null) {
					executeLock.writeLock().lock();
					scheduleLock.readLock().lock();
					lock = scheduleLock.readLock();
					executeLock.writeLock().unlock();
					billingDate = bp.getBillingProcessConfiguration().getNextBillingDate();
				}else{
					executeLock.readLock().lock();
					lock = executeLock.readLock();
					scheduleLock.writeLock().lock();
					scheduleLock.writeLock().unlock();
					billingDate = new Date();
				}
					
				InvoiceCount oic = orderService.orderBillingProcess(order, autoPay, billingDate, bp);
				if(!BigDecimal.ZERO.equals(oic.getInvoiceTotal())){
					bpr.setOrderTotal(
							NumberUtil.add(bpr.getOrderTotal(), 1)
					);
					bpr.setInvoiceTotal(
							NumberUtil.add(bpr.getInvoiceTotal(), oic.getInvoiceTotal())
					);
					bpr.setUnPaidTotal(
							NumberUtil.add(bpr.getUnPaidTotal(), oic.getBalance())
					);
					bpr.setPaidTotal(
							NumberUtil.minus(bpr.getInvoiceTotal(), bpr.getUnPaidTotal())
					);
				}
			}
		}finally{
			if(lock != null){
				lock.unlock();
			}
		}
		
		if(bpr.getInvoiceTotal().compareTo(BigDecimal.ZERO) < 0){
			throw new ApplicationRuntimeException("Invoice amount is less than zero for order : " + orderId);
		}
		
		return bpr;
	}

}
