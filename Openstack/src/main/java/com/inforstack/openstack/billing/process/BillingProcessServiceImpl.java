package com.inforstack.openstack.billing.process;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.billing.invoice.Invoice;
import com.inforstack.openstack.billing.invoice.InvoiceCount;
import com.inforstack.openstack.billing.invoice.InvoiceService;
import com.inforstack.openstack.billing.process.conf.BillingProcessConfiguration;
import com.inforstack.openstack.billing.process.result.BillingProcessResult;
import com.inforstack.openstack.billing.process.result.BillingProcessResultService;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.order.OrderService;
import com.inforstack.openstack.order.sub.Period;
import com.inforstack.openstack.order.sub.SubOrder;
import com.inforstack.openstack.order.sub.SubOrderService;
import com.inforstack.openstack.payment.PaymentService;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.NumberUtil;
import com.inforstack.openstack.utils.OpenstackUtil;

@Service("billingProcessService")
@Transactional
public class BillingProcessServiceImpl implements BillingProcessService {

	private static final Logger log = new Logger(BillingProcessServiceImpl.class);
	@Autowired
	private BillingProcessDao billingProcessDao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
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
	public BillingProcess processBillingProcess(BillingProcess billingProcess) {
		log.debug("Change billing process status to "
				+ Constants.BILLINGPROCESS_STATUS_PROCESSING + " : "
				+ billingProcess.getId());
		billingProcess.setStatus(Constants.BILLINGPROCESS_STATUS_PROCESSING);
		log.debug("Change billing process status successfully");
		
		return billingProcess;
	}

	@Override
	public BillingProcess processBillingProcess(int billingProcessId) {
		log.debug("Update billing process status");
		BillingProcess bp = billingProcessDao.findById(billingProcessId);
		if(bp == null){
			log.info("Update billing process status failed for no billing process found");
			return null;
		}
		bp.setStatus(Constants.BILLINGPROCESS_STATUS_PROCESSING);
		log.debug("Update billing process successfully");
		
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
	public void runBillingProcess(BillingProcessConfiguration conf) {
		log.debug("Running billing process for billing process configuration : " + conf.getId());
		BillingProcessService self = (BillingProcessService)OpenstackUtil.getBean("billingProcessService");
		BillingProcess bp = self.createBillingProcess(conf, new Date(), null);
		BillingProcessResult bpr = billingProcessResultService.createBillingProcessResult(bp);
		
		List<Order> orders = orderService.findAll(null, Constants.ORDER_STATUS_ACTIVE);
		for(Order order : orders){
			InvoiceCount oic = orderService.payOrder(order, conf.getNextBillingDate(), bp);
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
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(conf.getNextBillingDate());
		calendar.add(conf.getPeriodType(), conf.getPeriodQuantity());
		conf.setNextBillingDate(calendar.getTime());
		
		bp.setEndTime(new Date());
		bp.setStatus(Constants.BILLINGPROCESS_STATUS_SUCCESS);
		log.debug("Running billing process finished");
	}
	
}
