package com.inforstack.openstack.billing.process.conf;

import java.util.Date;
import java.util.List;

public interface BillingProcessConfigurationService {
	
	/**
	 * create billing process with necessary fileds
	 * @param name
	 * @param nextBillingDate
	 * @param retry
	 * @param daysBetweenRetry
	 * @param periodType
	 * @param periodQuantity
	 * @return
	 */
	public BillingProcessConfiguration createBillingProcessConfiguration(String name, Date nextBillingDate, 
			int periodType, int periodQuantity);
	
	/**
	 * Find billing process configuration
	 * @param billingProcessConfigurationId
	 * @return
	 */
	public BillingProcessConfiguration findBillingProcessConfigurationById(int billingProcessConfigurationId);
	
	/**
	 * remove billing process configuration
	 * @param bpc
	 * @return
	 */
	public BillingProcessConfiguration removeBillingProcessConfiguration(BillingProcessConfiguration bpc);
	
	/**
	 * remove billing process configuration
	 * @param billingProcessConfigurationId
	 * @return
	 */
	public BillingProcessConfiguration removeBillingProcessConfiguration(int billingProcessConfigurationId);
	
	/**
	 * list all billing process configuration
	 * @return
	 */
	public List<BillingProcessConfiguration> listAll();
}
