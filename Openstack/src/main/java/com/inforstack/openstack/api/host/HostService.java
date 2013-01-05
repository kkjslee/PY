package com.inforstack.openstack.api.host;

import com.inforstack.openstack.api.OpenstackAPIException;

public interface HostService {

	public final static String ENDPOINT_HOST	= "openstack.endpoint.hosts";
	
	public Host[] getHosts() throws OpenstackAPIException;
	
}
