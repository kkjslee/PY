package com.inforstack.openstack.billing.process.conf;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.CollectionUtil;

@Service
@Transactional
public class BillingProcessConfigurationServiceImpl implements
		BillingProcessConfigurationService {
	
	private static final Logger log = new Logger(BillingProcessConfigurationServiceImpl.class);
	@Autowired
	private BillingProcessConfigurationDao billingProcessConfigurationDao;
	
	@Override
	public BillingProcessConfiguration createBillingProcessConfiguration(String name,
			Date nextBillingDate, int periodType, int periodQuantity) {
		log.debug("Create billing process configuration with name : " + name + ", nextBillingDate : " +
			nextBillingDate + ", periodType : " + periodType + ", periodQuantity : " + periodQuantity);
		BillingProcessConfiguration bpc = new BillingProcessConfiguration();
		bpc.setCreateTime(new Date());
		bpc.setName(name);
		bpc.setNextBillingDate(nextBillingDate);
		bpc.setPeriodQuantity(periodQuantity);
		bpc.setPeriodType(periodType);
		
		billingProcessConfigurationDao.persist(bpc);
		log.debug("Create billing process configuration successfully");
		return bpc;
	}

	@Override
	public BillingProcessConfiguration findBillingProcessConfigurationById(int billingProcessConfigurationId) {
		log.debug("Find billing process configuration by id : " + billingProcessConfigurationId);
		
		BillingProcessConfiguration bpc = billingProcessConfigurationDao.findById(billingProcessConfigurationId);
		if(bpc == null){
			log.debug("No instance found by id : " + billingProcessConfigurationId);
		}else{
			log.debug("Find successfully");
		}
		
		return bpc;
	}
	
	public BillingProcessConfiguration removeBillingProcessConfiguration(BillingProcessConfiguration bpc){
		log.debug("Remove billing process configuration with id : " + bpc.getId());
		billingProcessConfigurationDao.remove(bpc);
		log.debug("Remove billing process configuration successfully");
		
		return bpc;
	}

	@Override
	public BillingProcessConfiguration removeBillingProcessConfiguration(
			int billingProcessConfigurationId) {
		log.debug("Remove billing process configuration by id : " + billingProcessConfigurationId);
		BillingProcessConfiguration bpc = billingProcessConfigurationDao.findById(billingProcessConfigurationId);
		if(bpc == null){
			log.info("Remove billing process configuration failed for no instance found by id : " + billingProcessConfigurationId);
			return null;
		}
		billingProcessConfigurationDao.remove(bpc);
		log.debug("Remove billing process configuration successfully");
		
		return bpc;
	}

	@Override
	public List<BillingProcessConfiguration> listAll() {
		log.debug("List all billing process configuration");
		List<BillingProcessConfiguration> bpcs = billingProcessConfigurationDao.list();
		if(CollectionUtil.isNullOrEmpty(bpcs)){
			log.debug("No instance found");
		}else{
			log.debug(bpcs.size() + " instance(s) found");
		}
		
		return bpcs;
	}
}
