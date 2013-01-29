package com.inforstack.openstack.billing.process;

import java.util.Date;

import com.inforstack.openstack.billing.process.conf.BillingProcessConfiguration;
import com.inforstack.openstack.user.User;

public interface BillingProcessService {
	
	public BillingProcess createBillingProcess(BillingProcess billingProcess);

	public BillingProcess createBillingProcess(BillingProcessConfiguration config, Date startTime, User user);
	
	public BillingProcess processingBillingProcess(BillingProcess billingProcess);
	
	public BillingProcess processingBillingProcess(int billingProcessId);
	
	public BillingProcess findBillingProcessById(int billingProcessId);
}
