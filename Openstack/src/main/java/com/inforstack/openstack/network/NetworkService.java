package com.inforstack.openstack.network;

import java.util.List;

import com.inforstack.openstack.item.DataCenter;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.user.User;

public interface NetworkService {
	
	public List<Network> listNetworks();
	
	public List<Network> listNetworksFromTenant(Tenant tenant, Integer dataCenterId);
	
	public List<Subnet> listSubnets();
	
	public List<Subnet> listSubnetsFromTenant(Tenant tenant);
	
	public List<Subnet> listSubnetsFromNetwork(Network network);

	public Network createNetwork(User user, Tenant tenant, String name, DataCenter dataCenter);
	
	public void removeNetwork(User user, Tenant tenant, String uuid);
	
	public Subnet createSubnet(User user, Tenant tenant, Network network, String name, DataCenter dataCenter);
	
	public void removeSubnet(User user, Tenant tenant, String uuid);
	
	public int[] generateAddress(DataCenter dataCenter);
	
}
