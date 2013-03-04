package com.inforstack.openstack.billing.process;

import java.util.Date;

import com.inforstack.openstack.billing.process.conf.BillingProcessConfiguration;
import com.inforstack.openstack.billing.process.result.BillingProcessResult;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.user.User;

public interface BillingProcessService {
	
	/**
	 * Create billing process
	 * @param billingProcess
	 * @return
	 */
	public BillingProcess createBillingProcess(BillingProcess billingProcess);
	
	/**
	 * Create billing porcess with necessary fields
	 * @param config
	 * @param startTime
	 * @param user
	 * @return
	 */
	public BillingProcess createBillingProcess(BillingProcessConfiguration config, Date startTime, User user);
	
	/**
	 * process billing process
	 * @param billingProcess
	 * @return
	 */
	public BillingProcess processBillingProcess(BillingProcess billingProcess);
	
	/**
	 * process billing process
	 * @param billingProcessId
	 * @return
	 */
	public BillingProcess processBillingProcess(int billingProcessId);
	
	/**
	 * find billing process
	 * @param billingProcessId
	 * @return
	 */
	public BillingProcess findBillingProcessById(int billingProcessId);
	
	public BillingProcessResult runBillingProcess(BillingProcessConfiguration conf);

	public BillingProcessResult runBillingProcess(BillingProcessConfiguration conf, Integer tenantId);

	public BillingProcessResult runBillingProcess(Integer tenantId);

	BillingProcessResult runBillingProcessForOrder(String orderId);

	void processOrder(String orderId, BillingProcess bp,
			BillingProcessResult bpr);
}
