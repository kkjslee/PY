package com.inforstack.openstack.virt.domain.usage;

import java.util.Date;

import com.inforstack.openstack.basic.BasicDao;

public interface DomainUsageDao extends BasicDao<DomainUsage>{

	DomainUsage find(Integer instanceId, String instanceUuid, Date logTime);

	DomainUsage sum(String uuid, Date start, Date end);

}
