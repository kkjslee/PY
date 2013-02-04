package com.inforstack.openstack.virt.domain;

import java.util.List;

import com.inforstack.openstack.basic.BasicDao;

public interface VirtDomainDao extends BasicDao<VirtDomain> {

	public List<VirtDomain> findVirtDomainsByTenant(int tenantId);

}
