package com.inforstack.openstack.virt.domain.usage;

public interface DomainUsageService {
	
	public void add(DomainUsage domainUsage);

	public boolean exists(DomainUsage domainUsage);
	
}
