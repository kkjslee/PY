package com.inforstack.openstack.billing.process.result;

import com.inforstack.openstack.billing.process.BillingProcess;

public interface BillingProcessResultService {
	
	public BillingProcessResult createBillingProcessResult(BillingProcess billingProcess);
	
	public BillingProcessResult findBillingProcessResult(int billingProcessId);

}
