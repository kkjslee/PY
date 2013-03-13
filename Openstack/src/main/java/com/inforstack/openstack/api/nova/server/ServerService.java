package com.inforstack.openstack.api.nova.server;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;

public interface ServerService {
	
	public final static String ENDPOINT_SERVERS			= "openstack.endpoint.servers";
	public final static String ENDPOINT_SERVERS_DETAIL	= "openstack.endpoint.servers.detail";
	public final static String ENDPOINT_SERVER			= "openstack.endpoint.server";
	public final static String ENDPOINT_SERVER_ACTION	= "openstack.endpoint.server.action";
	
	public Server[] listServers(Access access, boolean flaverAndImage) throws OpenstackAPIException;
	
	public Server getServer(Access access, String id, boolean flaverAndImage) throws OpenstackAPIException;
	
	public Server createServer(Access access, Server server) throws OpenstackAPIException;
	
	public void removeServer(Access access, String uuid) throws OpenstackAPIException;
	
	public void doServerAction(Access access, String uuid, ServerAction action) throws OpenstackAPIException;
	
	public String getVNCLink(Access access, String uuid, String type) throws OpenstackAPIException;
	
	public void resizeServer(Access access, String uuid, String flavorId) throws OpenstackAPIException;
	
	public void updateServerStatus(String uuid, String status, String task);
	
	public void updateServerAddress(String uuid, Server server);

}
