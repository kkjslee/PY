package com.inforstack.openstack.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.basic.BasicDaoImpl.CursorResult;
import com.inforstack.openstack.billing.invoice.Invoice;
import com.inforstack.openstack.billing.invoice.InvoiceCount;
import com.inforstack.openstack.billing.invoice.InvoiceService;
import com.inforstack.openstack.billing.process.BillingProcess;
import com.inforstack.openstack.billing.process.BillingProcessService;
import com.inforstack.openstack.controller.model.CartItemModel;
import com.inforstack.openstack.controller.model.CartModel;
import com.inforstack.openstack.controller.model.PaginationModel;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.instance.AttributeMap;
import com.inforstack.openstack.instance.InstanceService;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.order.sub.SubOrder;
import com.inforstack.openstack.order.sub.SubOrderService;
import com.inforstack.openstack.payment.PaymentService;
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
	@Autowired
	private InstanceService instanceService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private BillingProcessService billingProcessService;

	@Override
	public Order createOrder(Order order) {
		if (order == null) {
			log.info("Create order failed for passed order is null");
			return null;
		}
		log.debug("Create order");
		orderDao.persist(order);
		log.debug("Create order successfully");

		return order;
	}

	public Order createOrder(CartModel cartModel) {
		log.debug("Create order form cartModel");

		OrderService self = (OrderService) OpenstackUtil
				.getBean("orderService");
		Order o = self.createOrder(new BigDecimal(cartModel.getAmount()),
				SecurityUtils.getTenant(), null, null, true);
		if (o == null) {
			log.error("Create order failed");
			throw new ApplicationRuntimeException("Create Order failed");
		}

		for (CartItemModel itemModel : cartModel.getItems()) {
			for (int i = 0, n = itemModel.getNumber(); i < n; i++) {
				SubOrder so = subOrderService.createSubOrder(
						itemModel.getItemSpecificationId(), o.getId(),
						itemModel.getPeriodId());
				if (so == null) {
					log.error("Create sub order failed");
					throw new ApplicationRuntimeException(
							"Create sub order failed");
				}
				so.setPrice(BigDecimal.valueOf(itemModel.getPrice()));
				List<SubOrder> subOrders = o.getSubOrders();
				if (subOrders == null) {
					subOrders = new ArrayList<SubOrder>();
				}
				subOrders.add(so);
				o.setSubOrders(subOrders);
				AttributeMap.getInstance().put(so.getId(), "name",
						itemModel.getName());
			}
		}
		// TODO: create instances after checkout
		Integer tenantId = SecurityUtils.getTenantId();
		Integer userId = SecurityUtils.getUserId();
		User user = this.userService.findUserById(userId);
		Tenant tenant = this.tenantService.findTenantById(tenantId);
		this.instanceService.createInstance(user, tenant, o.getId());
		log.debug("Create order successfully");
		return o;
	}

	@Override
	public Order createOrder(BigDecimal amount, Tenant tenant, Date begin,
			Date end, boolean autoPay) {
		log.debug("Create order for tenant : " + tenant.getName());
		Date now = new Date();
		Order order = new Order();
		order.setActiveBegin(begin == null ? now : begin);
		if (end != null) {
			order.setActiveEnd(end);
		}
		User user = userService.findUserById(SecurityUtils.getUser().getId());
		if (user == null) {
			log.error("No user found in session");
			throw new ApplicationRuntimeException("No user found in session");
		}
		order.setCreatedBy(user);
		order.setCreateTime(now);
		order.setStatus(Constants.ORDER_STATUS_NEW);
		order.setAutoPay(autoPay);
		order.setTenant(tenant);
		order.setAmount(amount);
		order.setBalance(amount);
		orderDao.persist(order);
		log.debug("Create order successfully");

		return order;
	}

	@Override
	public Order createOrder(BigDecimal amount, int tenantId, Date begin,
			Date end, boolean autoPay) {
		log.debug("Create order for tenant : " + tenantId);
		Tenant tenant = tenantService.findTenantById(tenantId);
		if (tenant == null) {
			log.info("Create order failed for no tenant found by id : "
					+ tenantId);
			return null;
		}

		OrderService self = (OrderService) OpenstackUtil
				.getBean("orderService");
		Order order = self.createOrder(amount, tenant, begin, end, autoPay);
		if (order == null) {
			log.debug("Create order failed");
		} else {
			log.debug("Create order successfully");
		}

		return order;
	}

	@Override
	public Order cancelOrder(String orderId) {
		log.debug("Delete order : " + orderId);
		Order order = orderDao.findById(orderId);
		if (order == null) {
			log.info("Change order status failed for no instance found by order id : "
					+ orderId);
			return null;
		}
		billingProcessService.runBillingProcessForOrder(order.getId());
		order.setStatus(Constants.ORDER_STATUS_CALLELED);

		log.debug("Change order status successfully");
		return order;
	}

	@Override
	public Order findOrderById(String orderId) {
		log.debug("Find order by id : " + orderId);
		Order order = orderDao.findById(orderId);
		if (order == null) {
			log.debug("No instance found");
		} else {
			log.debug("Find order successfully");
		}

		return order;
	}

	@Override
	public CursorResult<Order> findAll(Integer tenantId, Integer status) {
		log.debug("Find all orders by tenant : " + tenantId + ", status : "
				+ status);
		CursorResult<Order> results = orderDao.find(tenantId, status);
		log.debug("Find successfully");
		return results;
	}

	@Override
	public InvoiceCount orderBillingProcess(Order order, Date billingDate,
			BillingProcess billingProcess) {
		log.debug("Billing process for order : " + order.getId()
				+ " with billing date : " + billingDate
				+ ", billing process : " + billingProcess == null ? null
				: billingProcess.getId());

		Integer periodId = null;
		if (billingProcess != null
				&& billingProcess.getBillingProcessConfiguration() != null) {
			periodId = billingProcess.getBillingProcessConfiguration()
					.getPeriodType();
		}
		
		InvoiceCount ic = new InvoiceCount();
		if(new Integer(Constants.ORDER_STATUS_NEW).equals(order.getStatus())){
			Invoice invoice = invoiceService.createInvoice(billingDate, billingDate, order.getAmount(), order.getTenant(), null, order, billingProcess);
			if(order.getAutoPay()){
				paymentService.applyPayment(invoice);
			}
			ic.addInvoiceTotal(invoice.getAmount());
			ic.addBalance(invoice.getBalance());
		}else if(new Integer(Constants.ORDER_STATUS_ACTIVE).equals(order.getStatus())){
			List<SubOrder> subOrders = subOrderService.findSubOrders(order.getId(),
					Constants.SUBORDER_STATUS_AVAILABLE, periodId);
			for (SubOrder so : subOrders) {
				InvoiceCount sic = subOrderService.billingProcessSubOrder(so,
						billingDate, billingProcess);
				ic.addInvoiceTotal(sic.getInvoiceTotal());
				ic.addBalance(ic.getBalance());
			}
			log.debug("Pay order successfully");
		}
		
		this.checkOrderStatus(order);
		
		return ic;
	}

	@Override
	public int checkOrderStatus(Order order) {
		if(Constants.ORDER_STATUS_NEW.equals(order.getStatus()) ||
				Constants.ORDER_STATUS_UNPAID.equals(order.getStatus()) ||
				Constants.ORDER_STATUS_CALLELED.equals(order.getStatus()) ||
				Constants.ORDER_STATUS_FINISHED.equals(order.getStatus()) ||
				Constants.ORDER_STATUS_PROCESSING.equals(order.getStatus()) ||
				Constants.ORDER_STATUS_READY.equals(order.getStatus())
				){
			return order.getStatus();
		}else if(Constants.ORDER_STATUS_ACTIVE.equals(order.getStatus())){
			List<SubOrder> subOrders = order.getSubOrders();
			for (SubOrder subOrder : subOrders) {
				if (Constants.SUBORDER_STATUS_AVAILABLE.equals(subOrder.getStatus()) || 
						Constants.SUBORDER_STATUS_ERROR.equals(subOrder.getStatus()) ||
						Constants.SUBORDER_STATUS_NEW.equals(subOrder.getStatus())) {
					return Constants.ORDER_STATUS_ACTIVE;
				}
			}

			order.setStatus(Constants.ORDER_STATUS_FINISHED);
			return Constants.ORDER_STATUS_FINISHED;
		}else{
			throw new ApplicationRuntimeException("Unsupported order status : " + order.getStatus());
		}
	}

	@Override
	public PaginationModel<Order> findAllWithCreator(int pageIndex,
			int pageSize, Integer tenantId, Integer status) {
		log.debug("Find all orders with creator by tenant : " + tenantId
				+ ", status : " + status);

		PaginationModel<Order> pm = orderDao.findWithCreator(pageIndex,
				pageSize, tenantId, status);
		if (CollectionUtil.isNullOrEmpty(pm.getData())) {
			log.debug("find failed");
		} else {
			for (Order o : pm.getData()) {
				o.getCreatedBy().getId();
			}
			log.debug("Find successfully");
		}

		return pm;
	}

	@Override
	public PaginationModel<Order> findAllWithoutSubOrder(int pageIndex,
			int pageSize) {
		log.debug("Find all orders without sub orders");

		PaginationModel<Order> pm = orderDao.findAllWithoutSubOrder(pageIndex,
				pageSize);
		if (CollectionUtil.isNullOrEmpty(pm.getData())) {
			log.debug("find failed");
		} else {
			for (Order o : pm.getData()) {
				o.getCreatedBy().getId();
				o.getTenant().getId();
			}
			log.debug("Find successfully");
		}

		return pm;
	}

}
