package com.inforstack.openstack.virt.domain;

import java.util.List;

import com.inforstack.openstack.tenant.Tenant;

public interface VirtDomainService {

	public List<VirtDomain> findTenantVirtDomains(Tenant tenant);
	
	public VirtDomain deleteVirtDoamin(VirtDomain virtDomain);
}
