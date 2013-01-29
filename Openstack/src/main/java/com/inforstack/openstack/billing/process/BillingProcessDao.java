package com.inforstack.openstack.billing.process;

public interface BillingProcessDao {

	public void persist(BillingProcess billingProcess);

	public BillingProcess findById(Integer billingProcessId);

}
