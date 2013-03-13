package com.inforstack.openstack.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.basic.BasicDaoImpl.CursorResult;
import com.inforstack.openstack.billing.invoice.Invoice;
import com.inforstack.openstack.billing.invoice.InvoiceCount;
import com.inforstack.openstack.billing.invoice.InvoiceService;
import com.inforstack.openstack.billing.process.BillingProcess;
import com.inforstack.openstack.billing.process.BillingProcessService;
import com.inforstack.openstack.billing.process.result.BillingProcessResult;
import com.inforstack.openstack.controller.model.CartItemModel;
import com.inforstack.openstack.controller.model.CartModel;
import com.inforstack.openstack.controller.model.PaginationModel;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.instance.AttributeMap;
import com.inforstack.openstack.instance.InstanceService;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.order.period.OrderPeriod;
import com.inforstack.openstack.order.sub.SubOrder;
import com.inforstack.openstack.order.sub.SubOrderService;
import com.inforstack.openstack.payment.Payment;
import com.inforstack.openstack.payment.PaymentService;
import com.inforstack.openstack.payment.method.PaymentMethodService;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.DateUtil;
import com.inforstack.openstack.utils.NumberUtil;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.SecurityUtils;
import com.inforstack.openstack.utils.StringUtil;

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
	private PaymentMethodService paymentMethodService;
	@Autowired
	private BillingProcessService billingProcessService;
	
	private static String cachedDate = null;
	private static int sequence = 0;
	
	@PostConstruct
	public void postInit(){
		synchronized (OrderServiceImpl.class){
			if(cachedDate == null){
				Date date = new Date();
				Order order = orderDao.findLastestBySequenceDate("sequence", date);
				int preLen = Constants.SEQUENCE_PREFIX_ORDER.length();
				if(order != null){
					cachedDate = order.getSequence().substring(0+preLen, DateUtil.SEQ_DATE_LEN + 1 + preLen);
					sequence = new Integer(order.getSequence().substring(DateUtil.SEQ_DATE_LEN + 1 + preLen));
				}else{
					cachedDate = DateUtil.getSequenceDate(date);
					sequence = 0;
				}
			}
		}
	}
	
	private String genenrateOrderSequence(){
		synchronized (OrderServiceImpl.class) {
			int max = new Integer(StringUtil.leftPadding("", '9', DateUtil.SEQ_DATE_LEN));
			if(sequence == max){
				throw new ApplicationRuntimeException("Max order limit reached today");
			}
			
			String date = DateUtil.getSequenceDate(new Date());
			if(!date.equals(cachedDate)){
				cachedDate = date;
				sequence = 0;
			}
			sequence++;
			return Constants.SEQUENCE_PREFIX_ORDER + date + NumberUtil.leftPaddingZero(sequence, 8);
		}
	}

	@Override
	public Order createOrder(CartModel cartModel) {
		log.debug("Create order form cartModel");

		Order o = this.createOrder(new BigDecimal(cartModel.getAmount()),
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
		order.setSequence(this.genenrateOrderSequence());
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

		Order order = this.createOrder(amount, tenant, begin, end, autoPay);
		if (order == null) {
			log.debug("Create order failed");
		} else {
			log.debug("Create order successfully");
		}

		return order;
	}

	@Override
	public Order cancelOrder(int orderId) {
		log.debug("Delete order : " + orderId);
		Order order = orderDao.findById(orderId);
		if (order == null) {
			log.info("Change order status failed for no instance found by order id : "
					+ orderId);
			return null;
		}
		Date now = new Date();
		for(SubOrder so : order.getSubOrders()){
			so.setNextBillingDate(now);
		}
		billingProcessService.runBillingProcessForOrder(order.getId(), order.getAutoPay());
		order.setStatus(Constants.ORDER_STATUS_CALLELED);

		log.debug("Change order status successfully");
		return order;
	}

	@Override
	public Order findOrderById(int orderId) {
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
	public CursorResult<Integer> findAll(Integer tenantId, Integer status) {
		return this.findAll(tenantId, null, status);
	}
	
	@Override
	public CursorResult<Integer> findAll(Integer tenantId, Date billingDate,
			Integer status) {
		log.debug("Find all orders by tenant : " + tenantId + ", status : "
				+ status);
		CursorResult<Integer> results = orderDao.find(tenantId, billingDate, status);
		log.debug("Find successfully");
		return results;
	}

	
	@Override
	public CursorResult<Integer> findAll(
			List<Integer> orderPeriods, Date billingDate, Integer status) {
		log.debug("Find all orders by tenant : " + CollectionUtil.toString(orderPeriods) 
				+ ", status : " + status);
		CursorResult<Integer> results = orderDao.find(orderPeriods, billingDate, status);
		log.debug("Find successfully");
		return results;
	}

	@Override
	public InvoiceCount orderBillingProcess(Order order, Boolean autoPay, Date billingDate,
			BillingProcess billingProcess) {
		log.debug("Billing process for order : " + order.getId()
				+ " with billing date : " + billingDate
				+ ", billing process : " + billingProcess == null ? null
				: billingProcess.getId());

		InvoiceCount ic = new InvoiceCount();
		if(new Integer(Constants.ORDER_STATUS_NEW).equals(order.getStatus())){
			Invoice invoice = invoiceService.createInvoice(billingDate, billingDate, order.getAmount(), order.getTenant(), null, order, billingProcess);
			order.setInvoice(invoice);
			
			boolean doPay = order.getAutoPay();
			if(autoPay != null){
				doPay = autoPay.booleanValue();
			}
			if(doPay){
				paymentService.applyPayment(invoice, Constants.PAYMENT_TYPE_ORDER, false);
			}
			ic.addInvoiceTotal(invoice.getAmount());
			ic.addBalance(invoice.getBalance());
		}else if(new Integer(Constants.ORDER_STATUS_ACTIVE).equals(order.getStatus())){
			List<Integer> statuses = new ArrayList<Integer>();
			statuses.add(Constants.SUBORDER_STATUS_AVAILABLE);
			
			List<SubOrder> subOrders = subOrderService.findSubOrders(order.getId(),
					statuses, null);
			for (SubOrder so : subOrders) {
				InvoiceCount sic = subOrderService.billingProcessSubOrder(so, autoPay,
						billingDate, billingProcess);
				ic.addInvoiceTotal(sic.getInvoiceTotal());
				ic.addBalance(ic.getBalance());
			}
			log.debug("Pay order successfully");
		}
		
		if(ic.getInvoiceTotal().compareTo(BigDecimal.ZERO) > 0){
			order.setLastBillingTime(new Date());
			this.checkOrderStatus(order);
		}
		
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
						Constants.SUBORDER_STATUS_OVERDUE.equals(subOrder.getStatus()) ||
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

	@Override
	public String payOrder(int orderId, int paymentMethodId, Map<String, Object> property) {
		Order order = orderDao.findById(orderId);
		if(order == null){
			log.error("No order found by order id : " +orderId);
			throw new ApplicationRuntimeException("Order not found");
		}
		BillingProcessResult bpr = billingProcessService.runBillingProcessForOrder(orderId, false);
		orderDao.refresh(order);
		Payment payment = paymentService.createPayment(
				order.getSequence(), bpr.getUnPaidTotal().negate(),
				Constants.PAYMENT_TYPE_AUTHORISATION, order.getTenant().getId(), null);
		
		if(property == null){
			property = new HashMap<String, Object>();
		}
		property.put(Constants.PAYMENTMETHODPROPERTY_NAME_PAYMENT, payment);
		return paymentService.generateEndpoint(paymentMethodId, order.getInvoice().getBalance(), property);
	}

	@Override
	public Order findOrderBySequence(String subject) {
		return orderDao.findByObject("sequence", subject);
	}

	@Override
	public boolean fullPayment(int orderId, int paymentId) {
		Order order = orderDao.findById(orderId);
		Payment payment = paymentService.findPaymentById(paymentId);
		if(order == null || payment == null){
			return false;
		}
		
		if(!payment.getAccount().getTenant().getId().equals(order.getTenant().getId())){
			return false;
		}
		
		if(payment.getAmount().compareTo(payment.getAccount().getAmount()) > 0 ){
			return false;
		}
		if(payment.getCatalog() != Constants.PAYMENT_CATALOG_PAYOUT){
			throw new ApplicationRuntimeException(OpenstackUtil.getMessage("payment.type.not.support"));
		}
		
		if(order.getStatus() == Constants.ORDER_STATUS_NEW){
			BigDecimal balance = paymentService.applyPayment(order.getInvoice(), payment);
			if(balance.compareTo(BigDecimal.ZERO) != 0){
				throw new ApplicationRuntimeException("Full payment failed");
			}
			paymentService.paidSuccessfully(payment);
		}else{
			List<Invoice> invoices = invoiceService.findUnPaidInvoicesByOrder(orderId);
			if(invoices != null) {
				for(Invoice invoice : invoices){
					if(invoiceService.fullPayment(invoice.getId(), paymentId) == false){
						throw new ApplicationRuntimeException("Full payment failed");
					}
				}
			}
		}
		
		return true;
	}

}
