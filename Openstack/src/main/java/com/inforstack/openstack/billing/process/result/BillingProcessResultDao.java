package com.inforstack.openstack.billing.process.result;

public interface BillingProcessResultDao {

	public void persist(BillingProcessResult billingProcessResult);

	public BillingProcessResult findById(int billingProcessId);
	
}
