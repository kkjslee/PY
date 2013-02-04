package com.inforstack.openstack.billing.process.result;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inforstack.openstack.billing.process.BillingProcess;
import com.inforstack.openstack.log.Logger;

@Service
@Transactional
public class BillingProcessResultServiceImpl implements
		BillingProcessResultService {
	
	private static final Logger log = new Logger(BillingProcessResultServiceImpl.class);
	@Autowired
	private BillingProcessResultDao billingProcessResultDao;

	@Override
	public BillingProcessResult createBillingProcessResult(BillingProcess billingProcess) {
		log.debug("Create billing process result");
		BillingProcessResult billingProcessResult = new BillingProcessResult();
		billingProcessResult.setBillingProcess(billingProcess);
		billingProcessResult.setInvoiceTotal(new BigDecimal(0));
		billingProcessResult.setOrderTotal(0);
		billingProcessResult.setPaidTotal(new BigDecimal(0));
		billingProcessResult.setUnPaidTotal(new BigDecimal(0));
		billingProcessResultDao.persist(billingProcessResult);
		log.debug("Create billing process successfully");
		
		return billingProcessResult;
		
	}

	@Override
	public BillingProcessResult findBillingProcessResult(int billingProcessId) {
		log.debug("Find billing process result by id : " + billingProcessId);
		BillingProcessResult bpr = billingProcessResultDao.findById(billingProcessId);
		if(bpr == null){
			log.debug("No billing process result found");
		}else{
			log.debug("Find billing process result successfully");
		}
		
		return bpr;
	}

}
