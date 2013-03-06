package com.inforstack.openstack.network;

import java.util.List;

import com.inforstack.openstack.basic.BasicDao;
import com.inforstack.openstack.tenant.Tenant;

public interface SubnetDao extends BasicDao<Subnet> {
	
	public List<Subnet> listSubnets(Tenant tenant, Network network, Integer dataCenterId);

}
