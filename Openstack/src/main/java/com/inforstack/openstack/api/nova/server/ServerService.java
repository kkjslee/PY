package com.inforstack.openstack.api.nova.server;

import com.inforstack.openstack.api.OpenstackAPIException;

public interface ServerService {
	
	public final static String ENDPOINT_SERVERS			= "openstack.endpoint.servers";
	public final static String ENDPOINT_SERVERS_DETAIL	= "openstack.endpoint.servers.detail";
	public final static String ENDPOINT_SERVER			= "openstack.endpoint.server";
	
	public Server[] listServers(String tenant) throws OpenstackAPIException;

}