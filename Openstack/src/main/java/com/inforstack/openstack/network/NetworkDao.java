package com.inforstack.openstack.network;

import java.util.List;

import com.inforstack.openstack.basic.BasicDao;
import com.inforstack.openstack.tenant.Tenant;

public interface NetworkDao extends BasicDao<Network> {

	public List<Network> listNetworks(Tenant tenant, Integer dataCenterId);
	
}
