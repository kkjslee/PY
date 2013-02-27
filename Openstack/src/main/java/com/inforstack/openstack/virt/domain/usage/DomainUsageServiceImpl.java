package com.inforstack.openstack.virt.domain.usage;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inforstack.openstack.log.Logger;

@Service("domainUsageService")
@Transactional
public class DomainUsageServiceImpl implements DomainUsageService {
	
	private static final Logger log = new Logger(DomainUsageServiceImpl.class);
	@Autowired
	private DomainUsageDao domainUsageDao;

	@Override
	public void add(DomainUsage domainUsage) {
		log.debug("Add domain usage for domain : " + domainUsage.getInstanceUuid());
		domainUsageDao.persist(domainUsage);
		log.debug("Add successfully");
	}

	@Override
	public boolean exists(DomainUsage domainUsage) {
		log.debug("Check domain existence");
		DomainUsage du = domainUsageDao.find(
				domainUsage.getInstanceId(), 
				domainUsage.getInstanceUuid(), 
				domainUsage.getLogTime());
		if(du != null){
			log.debug("check successfully");
			return true;
		}
		
		log.debug("No instance exists");
		return false;
	}

}
