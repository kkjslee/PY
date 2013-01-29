package com.inforstack.openstack.billing.process.conf;

import java.util.Date;
import java.util.List;

public interface BillingProcessConfigurationService {
	
	public BillingProcessConfiguration createBillingProcessConfiguration(String name, Date nextBillingDate, 
			int retry, int daysBetweenRetry, int periodType, int periodQuantity);
	
	public BillingProcessConfiguration findBillingProcessConfigurationById(int billingProcessConfigurationId);
	
	public BillingProcessConfiguration removeBillingProcessConfiguration(BillingProcessConfiguration bpc);
	
	public BillingProcessConfiguration removeBillingProcessConfiguration(int billingProcessConfigurationId);
	
	public List<BillingProcessConfiguration> listAll();
}
