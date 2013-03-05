package com.inforstack.openstack.item;

import com.inforstack.openstack.basic.BasicDao;

public interface NetworkTypeDao extends BasicDao<NetworkType> {

	public boolean isWebSite(int dataCenterId, String uuid);
	
}
