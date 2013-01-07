package com.inforstack.openstack.api.nova.host;

import com.inforstack.openstack.api.OpenstackAPIException;

public interface HostService {

	public final static String ENDPOINT_HOSTS			= "openstack.endpoint.hosts";
	public final static String ENDPOINT_HOST_DESCRIBE	= "openstack.endpoint.host.describe";
	
	public Host[] listHosts() throws OpenstackAPIException;
	
	public HostDescribe[] getHostDescribes(Host host) throws OpenstackAPIException;
	
}
