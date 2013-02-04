package com.inforstack.openstack.virt.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.Constants;

public class VirtDomainServiceImpl implements VirtDomainService {
	
	private static final Logger log = new Logger(VirtDomainServiceImpl.class);
	
	@Autowired
	private VirtDomainDao virtDomainDao;

	@Override
	public List<VirtDomain> findTenantVirtDomains(Tenant tenant) {
		log.debug("Find virt domain by tenant : " + tenant.getId());
		List<VirtDomain> virtDomians = virtDomainDao.findVirtDomainsByTenant(tenant.getId());
		if(CollectionUtil.isNullOrEmpty(virtDomians)){
			log.debug("No virt domain found");
			return new ArrayList<VirtDomain>();
		}else{
			log.debug(virtDomians.size() + " virt domains found");
			return virtDomians;
		}
	}

	@Override
	public VirtDomain deleteVirtDoamin(VirtDomain virtDomain) {
		log.debug("Delete virt domain : " + virtDomain.getId());
		virtDomain.setStatus(Constants.VIRTDOMAIN_STATUS_DELETED);
		log.debug("Delete virt domain successfully");
		
		return virtDomain;
	}

}
