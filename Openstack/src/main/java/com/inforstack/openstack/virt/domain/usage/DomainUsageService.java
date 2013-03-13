package com.inforstack.openstack.virt.domain.usage;

import java.util.Date;

public interface DomainUsageService {
	
	public void add(DomainUsage domainUsage);

	public boolean exists(DomainUsage domainUsage);

	public DomainUsage sumUsage(String uuid, Date start, Date end);
	
}
