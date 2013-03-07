package com.inforstack.openstack.order.sub;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.inforstack.openstack.billing.invoice.InvoiceCount;
import com.inforstack.openstack.billing.process.BillingProcess;
import com.inforstack.openstack.order.period.OrderPeriod;

public interface SubOrderService {
	
	/**
	 * Create sub order by necessary fileds
	 * @param itemId
	 * @param orderId
	 * @param periodId
	 * @return
	 */
	public SubOrder createSubOrder(int itemId, String orderId, int periodId);
	
	/**
	 * find sub order by id
	 * @param subOrderId
	 * @return
	 */
	public SubOrder findSubOrderById(int subOrderId);
	
	/**
	 * find sub orders by order id and/or status
	 * @param orderId
	 * @param status
	 * @return
	 */
	public List<SubOrder> findSubOrders(String orderId, List<Integer> statuses, List<Integer> orderPeriods);
	
	/**
	 * delete sub order by id
	 * @param subOrderId
	 * @return
	 */
	public SubOrder deleteSubOrder(int subOrderId);

	/**
	 * calculate the period for the given sub order
	 * @param billingDate
	 * @return
	 */
	public List<Period> calcPeriod(SubOrder subOrder, Date billingDate, Date endLimit);

	public InvoiceCount billingProcessSubOrder(SubOrder subOrder, Date billingDate, BillingProcess billingProcess);

	public BigDecimal getPrice(SubOrder subOrder, Period period);

	public SubOrder findFirstSubOrderByInstanceId(int id);
	
}
