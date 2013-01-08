package com.inforstack.openstack.api.nova.server;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;

public interface ServerService {
	
	public final static String ENDPOINT_SERVERS			= "openstack.endpoint.servers";
	public final static String ENDPOINT_SERVERS_DETAIL	= "openstack.endpoint.servers.detail";
	public final static String ENDPOINT_SERVER			= "openstack.endpoint.server";
	
	public Server[] listServers(Access access) throws OpenstackAPIException;
	
	public Server createServer(Access access, Server server) throws OpenstackAPIException;
	
	public void removeServer(Access access, Server server) throws OpenstackAPIException;

}
