package com.inforstack.openstack.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.inforstack.openstack.basic.BasicDaoImpl.CursorResult;
import com.inforstack.openstack.billing.invoice.InvoiceCount;
import com.inforstack.openstack.billing.process.BillingProcess;
import com.inforstack.openstack.controller.model.CartModel;
import com.inforstack.openstack.controller.model.PaginationModel;
import com.inforstack.openstack.payment.Payment;
import com.inforstack.openstack.tenant.Tenant;

public interface OrderService {

	public Order createOrder(CartModel cartModel);
	
	/**
	 * create order with the necessary fields
	 * @param tenant
	 * @param begin
	 * @param end
	 * @return
	 */
	public Order createOrder(BigDecimal amount, Tenant tenant, Date begin, Date end, boolean autoPay);
	
	/**
	 * create order with the necessary fields
	 * @param tenantId
	 * @param begin
	 * @param end
	 * @return
	 */
	public Order createOrder(BigDecimal amount, int tenantId, Date begin, Date end, boolean autoPay);
	
	/**
	 * delete order
	 * @param orderId
	 * @param status
	 * @return
	 */
	public Order cancelOrder(int orderId);

	/**
	 * find order by id
	 * @param orderId
	 * @return
	 */
	public Order findOrderById(int orderId);
	
	/**
	 * find all orders by tenantId and/or status
	 * @param tenantId
	 * @param status
	 * @return
	 */
	public CursorResult<Integer> findAll(Integer tenantId, Integer status);
	
	public CursorResult<Integer> findAll(Integer tenantId, Date billingDate,
			Integer status);

	public CursorResult<Integer> findAll(List<Integer> orderPeriods,
			Date billingDate, Integer status);
	
	/**
	 * 
	 * @param order
	 * @param periodType
	 */
	public InvoiceCount orderBillingProcess(Order order, Boolean autoPay, Date billingDate, BillingProcess billingProcess);

	public int checkOrderStatus(Order order);

	public PaginationModel<Order> findAllWithCreator(int pageIndex, int pageSize, Integer tenantId, Integer status);

	public PaginationModel<Order> findAllWithoutSubOrder(int pageIndex, int pageSize);

	public String payOrder(int orderId, int paymentMethodId, Map<String, Object> property);

	public Order findOrderBySequence(String subject);

	public boolean fullPayment(int orderId, int paymentId);

}
