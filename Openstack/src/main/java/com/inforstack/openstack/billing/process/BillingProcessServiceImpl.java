package com.inforstack.openstack.billing.process;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.billing.process.conf.BillingProcessConfiguration;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.utils.Constants;

@Service
@Transactional
public class BillingProcessServiceImpl implements BillingProcessService {

	private static final Log log = LogFactory.getLog(BillingProcessServiceImpl.class);
	@Autowired
	private BillingProcessDao billingProcessDao;
	
	@Override
	public BillingProcess createBillingProcess(BillingProcess billingProcess) {
		log.debug("Create billing Process");
		billingProcessDao.persist(billingProcess);
		log.debug("Create billing process successfully");
		
		return billingProcess;
	}

	@Override
	public BillingProcess createBillingProcess(
			BillingProcessConfiguration config, Date startTime, User user) {
		log.debug("Create billing process");
		BillingProcess bp =  new BillingProcess();
		bp.setBillingProcessConfiguration(config);
		bp.setStartTime(startTime);
		bp.setRetry(0);
		bp.setStatus(Constants.BILLINGPROCESS_STATUS_NEW);
		if(user != null){
			bp.setUser(user);
		}
		billingProcessDao.persist(bp);
		log.debug("Create billing process failed");
		
		return bp;
	}
	
	@Override
	public BillingProcess processingBillingProcess(BillingProcess billingProcess) {
		log.debug("Change billing process status to "
				+ Constants.BILLINGPROCESS_STATUS_PROCESSING + " : "
				+ billingProcess.getId());
		billingProcess.setStatus(Constants.BILLINGPROCESS_STATUS_PROCESSING);
		log.debug("Change billing process status successfully");
		
		return billingProcess;
	}

	@Override
	public BillingProcess processingBillingProcess(int billingProcessId) {
		log.debug("Update billing process status");
		BillingProcess bp = billingProcessDao.findById(billingProcessId);
		if(bp == null){
			log.info("Update billing process status failed for no billing process found");
			return null;
		}
		bp.setStatus(Constants.BILLINGPROCESS_STATUS_PROCESSING);
		log.debug("Update billing process successfully");
		
		return bp;
	}

	@Override
	public BillingProcess findBillingProcessById(int billingProcessId) {
		log.debug("Find billing process by id : " + billingProcessId);
		BillingProcess bp = billingProcessDao.findById(billingProcessId);
		if(bp == null){
			log.debug("Find billing process failed");
		}else{
			log.debug("Find billing process successfully");
		}
		
		return bp;
	}
	
}
