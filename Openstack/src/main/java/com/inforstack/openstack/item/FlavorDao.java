package com.inforstack.openstack.item;

import com.inforstack.openstack.basic.BasicDao;

public interface FlavorDao extends BasicDao<Flavor> {
	
	public String getFlavorRefId(int dataCenterId, String uuid);

}
