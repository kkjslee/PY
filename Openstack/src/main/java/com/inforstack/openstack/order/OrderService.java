package com.inforstack.openstack.order;

import java.util.Date;
import java.util.List;

import com.inforstack.openstack.billing.invoice.InvoiceCount;
import com.inforstack.openstack.billing.process.BillingProcess;
import com.inforstack.openstack.tenant.Tenant;

public interface OrderService {

	/**
	 * create order
	 * @param order
	 * @return
	 */
	public Order createOrder(Order order);
	
	/**
	 * create order with the necessary fields
	 * @param tenant
	 * @param begin
	 * @param end
	 * @return
	 */
	public Order createOrder(Tenant tenant, Date begin, Date end, boolean autoPay);
	
	/**
	 * create order with the necessary fields
	 * @param tenantId
	 * @param begin
	 * @param end
	 * @return
	 */
	public Order createOrder(int tenantId, Date begin, Date end, boolean autoPay);
	
	/**
	 * delete order
	 * @param orderId
	 * @param status
	 * @return
	 */
	public Order cancelOrder(String orderId);

	/**
	 * find order by id
	 * @param orderId
	 * @return
	 */
	public Order findOrderById(String orderId);
	
	/**
	 * find all orders by tenantId and/or status
	 * @param tenantId
	 * @param status
	 * @return
	 */
	public List<Order> findAll(Integer tenantId, Integer status);
	
	/**
	 * 
	 * @param order
	 * @param periodType
	 */
	public InvoiceCount payOrder(Order order, Date billingDate, BillingProcess billingProcess);

	public boolean checkOrderFinished(Order order, Date date);
}
