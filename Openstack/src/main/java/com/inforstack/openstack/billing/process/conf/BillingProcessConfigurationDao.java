package com.inforstack.openstack.billing.process.conf;

import java.util.List;

public interface BillingProcessConfigurationDao {

	public void persist(BillingProcessConfiguration bpc);

	public BillingProcessConfiguration findById(Integer billingProcessConfigurationId);

	public void remove(BillingProcessConfiguration bpc);

	public List<BillingProcessConfiguration> findAll();

}
