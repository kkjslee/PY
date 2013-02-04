package com.inforstack.openstack.api.quantum;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.quantum.Subnet.AllocationPool;

public interface QuantumService {
	
	public final static String ENDPOINT_NETWORKS		= "openstack.endpoint.networks";
	public final static String ENDPOINT_NETWORK			= "openstack.endpoint.network";
	
	public final static String ENDPOINT_SUBNETS			= "openstack.endpoint.subnets";
	public final static String ENDPOINT_SUBNET			= "openstack.endpoint.subnet";
	
	public final static String ENDPOINT_PORTS			= "openstack.endpoint.ports";
	public final static String ENDPOINT_PORT			= "openstack.endpoint.port";
	
	public Network[] listNetworks(Access access) throws OpenstackAPIException;
	
	public Network getNetwork(Access access, String id) throws OpenstackAPIException;
	
	public Network createNetwork(Access access, String name, boolean adminStateUp) throws OpenstackAPIException;
	
	public void removeNetwork(Access access, String id) throws OpenstackAPIException;
	
	public Subnet[] listSubnets(Access access) throws OpenstackAPIException;
	
	public Subnet getSubnet(Access access, String id) throws OpenstackAPIException;
	
	public Subnet createSubnet(Access access, String network, int ipVer, String cidr, AllocationPool[] pools) throws OpenstackAPIException;
	
	public void removeSubnet(Access access, String id) throws OpenstackAPIException;
	
	public Port[] listPorts(Access access) throws OpenstackAPIException;
	
	public Port getPort(Access access, String id) throws OpenstackAPIException;
	
}
