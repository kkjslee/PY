package com.inforstack.openstack.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.billing.process.BillingProcess;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.order.sub.SubOrder;
import com.inforstack.openstack.order.sub.SubOrderService;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.SecurityUtils;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {
	
	private static final Logger log = new Logger(OrderServiceImpl.class);
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private UserService userService;
	@Autowired
	private TenantService tenantService;
	@Autowired
	private SubOrderService subOrderService;
	
	@Override
	public Order createOrder(Order order) {
		if(order == null){
			log.info("Create order failed for passed order is null");
			return null;
		}
		log.debug("Create order");
		orderDao.persist(order);
		log.debug("Create order successfully");
		
		return order;
	}
	
	@Override
	public Order createOrder(Tenant tenant, Date begin, Date end, boolean autoPay) {
		log.debug("Create order for tenant : " + tenant.getName());
		Date now  = new Date();
		Order order = new Order();
		order.setActiveBegin(begin==null?now : begin);
		if(end != null){
			order.setActiveEnd(end);
		}
		User user = userService.findUserById(SecurityUtils.getUser().getId());
		if(user == null){
			log.error("No user found in session");
			throw new ApplicationRuntimeException("No user found in session");
		}
		order.setCreatedBy(user);
		order.setCreateTime(now);
		order.setStatus(Constants.ORDER_STATUS_NEW);
		order.setAutoPay(autoPay);
		order.setTenant(tenant);
		orderDao.persist(order);
		log.debug("Create order successfully");
		
		return order;
	}

	@Override
	public Order createOrder(int tenantId, Date begin, Date end, boolean autoPay) {
		log.debug("Create order for tenant : " + tenantId);
		Tenant tenant = tenantService.findTenantById(tenantId);
		if(tenant == null){
			log.info("Create order failed for no tenant found by id : " + tenantId);
			return null;
		}
		
		OrderService self = (OrderService)OpenstackUtil.getBean("orderService");
		Order order = self.createOrder(tenant, begin, end, autoPay);
		if(order == null){
			log.debug("Create order failed");
		}else{
			log.debug("Create order successfully");
		}
		
		return order;
	}

	@Override
	public Order cancelOrder(String orderId) {
		log.debug("Delete order : " + orderId);
		Order order = orderDao.findById(orderId);
		if(order == null){
			log.info("Change order status failed for no instance found by order id : " + orderId);
			return null;
		}
		order.setStatus(Constants.ORDER_STATUS_CALLELED);
		
		log.debug("Change order status successfully");
		return order;
	}

	@Override
	public Order findOrderById(String orderId) {
		log.debug("Find order by id : " + orderId);
		Order order = orderDao.findById(orderId);
		if(order == null){
			log.debug("No instance found");
		}else{
			log.debug("Find order successfully");
		}
		
		return order;
	}

	@Override
	public List<Order> findAll(Integer tenantId, Integer status) {
		log.debug("Find all orders by tenant : " + tenantId + ", status : " + status);
		List<Order> orders = orderDao.find(tenantId, status);
		if(CollectionUtil.isNullOrEmpty(orders)){
			log.debug("find failed");
			return new ArrayList<Order>();
		}else{
			log.debug("Find successfully");
			return orders;
		}
	}

	@Override
	public void payOrder(Order order, Date billingDate, BillingProcess billingProcess) {
		log.debug("Pay order : " +order.getId() + " with billing date : " + billingDate + 
				", billing process : " + billingProcess==null?null:billingProcess.getId());
		List<SubOrder> subOrders = subOrderService.findSubOrders(
				order.getId(), 
				Constants.SUBORDER_STATUS_AVAILABLE,
				billingProcess==null? null : billingProcess.getBillingProcessConfiguration().getPeriodType());
		for(SubOrder so : subOrders){
			subOrderService.paySubOrder(so, billingDate, billingProcess);
		}
		log.debug("Pay order successfully");
	}

	@Override
	public void checkOrderFinished(Order order, Date date) {
		List<SubOrder> subOrders = order.getSubOrders();
		for(SubOrder subOrder : subOrders){
			if(subOrder.getStatus() == Constants.SUBORDER_STATUS_AVAILABLE){
				return;
			}
		}
		
		order.setStatus(Constants.ORDER_STATUS_FINISHED);
	}
	
}
