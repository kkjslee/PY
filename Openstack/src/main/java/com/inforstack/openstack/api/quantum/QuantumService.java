package com.inforstack.openstack.api.quantum;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.nova.server.Server;
import com.inforstack.openstack.api.quantum.Subnet.AllocationPool;

public interface QuantumService {

	public final static String ENDPOINT_NETWORKS = "openstack.endpoint.networks";
	public final static String ENDPOINT_NETWORK = "openstack.endpoint.network";

	public final static String ENDPOINT_SUBNETS = "openstack.endpoint.subnets";
	public final static String ENDPOINT_SUBNET = "openstack.endpoint.subnet";

	public final static String ENDPOINT_PORTS = "openstack.endpoint.ports";
	public final static String ENDPOINT_PORT = "openstack.endpoint.port";
	
	public final static String ENDPOINT_ROUTERS = "openstack.endpoint.routers";
	public final static String ENDPOINT_ROUTER_INTERFACE_ADD = "openstack.endpoint.router.interface.add";
	public final static String ENDPOINT_ROUTER_INTERFACE_REMOVE = "openstack.endpoint.router.interface.remove";
	
	public final static String ENDPOINT_FLOATINGIPS = "openstack.endpoint.floatingips";
	public final static String ENDPOINT_FLOATINGIP = "openstack.endpoint.floatingip";	
	
	public Network[] listNetworks(Access access) throws OpenstackAPIException;

	public Network getNetwork(Access access, String id) throws OpenstackAPIException;

	public Network createNetwork(Access access, String name, boolean adminStateUp, boolean shared, boolean external) throws OpenstackAPIException;

	public void updateNetwork(Access access, Network network, String name, boolean adminStateUp, boolean shared, boolean external) throws OpenstackAPIException;

	public void removeNetwork(Access access, String id) throws OpenstackAPIException;
	
	public Subnet[] listSubnets(Access access) throws OpenstackAPIException;

	public Subnet getSubnet(Access access, String id) throws OpenstackAPIException;

	public Subnet createSubnet(Access access, String network, String name, int ipVer, String cidr, AllocationPool[] pools) throws OpenstackAPIException;

	public void updateSubnet(Access access, Subnet subnet) throws OpenstackAPIException;

	public void removeSubnet(Access access, String id) throws OpenstackAPIException;

	public Port[] listPorts(Access access) throws OpenstackAPIException;

	public Port getPort(Access access, String id) throws OpenstackAPIException;

	public Port createPort(Access access, Port port, Server server) throws OpenstackAPIException;

	public void removePort(Access access, String id) throws OpenstackAPIException;
	
	public Router createRouter(Access access, String name, String external) throws OpenstackAPIException;
	
	public void addInterface(Access access, Router router, Subnet subnet) throws OpenstackAPIException;
	
	public FloatingIP createFloatingIP(Access access, String external) throws OpenstackAPIException;
	
	public void associateFloatingIP(Access access, String server, String uuid) throws OpenstackAPIException;
	
	public void disassociateFloatingIP(Access access, String uuid) throws OpenstackAPIException;
	
	public void removeFloatingIP(Access access, String id) throws OpenstackAPIException;
	
}
