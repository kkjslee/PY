package com.inforstack.openstack.order;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.SecurityUtils;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {
	
	private static final Log log = LogFactory.getLog(OrderServiceImpl.class);
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private UserService userService;
	@Autowired
	private TenantService tenantService;
	
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
	public Order createOrder(Tenant tenant, Date begin, Date end) {
		if(tenant == null){
			log.info("Create order failed for passed tenant is null");
			return null;
		}
		
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
		order.setTenant(tenant);
		orderDao.persist(order);
		log.debug("Create order successfully");
		
		return order;
	}

	@Override
	public Order createOrder(Integer tenantId, Date begin, Date end) {
		if(tenantId == null){
			log.info("Create order failed for passed tenant id is null");
			return null;
		}
		
		log.debug("Create order for tenant : " + tenantId);
		Tenant tenant = tenantService.findTenantById(tenantId);
		if(tenant == null){
			log.info("Create order failed for no tenant found by id : " + tenantId);
			return null;
		}
		
		OrderService self = (OrderService)OpenstackUtil.getBean("orderService");
		Order order = self.createOrder(tenant, begin, end);
		if(order == null){
			log.debug("Create order failed");
		}else{
			log.debug("Create order successfully");
		}
		
		return order;
	}
	
}
