package com.inforstack.openstack.api.nova.flavor;

import com.inforstack.openstack.api.OpenstackAPIException;

public interface FlavorService {

	public final static String ENDPOINT_FLAVORS			= "openstack.endpoint.flavors";
	public final static String ENDPOINT_FLAVORS_DETAIL	= "openstack.endpoint.flavors.detail";
	public final static String ENDPOINT_FLAVOR			= "openstack.endpoint.flavor";
	
	public Flavor[] listFlavors() throws OpenstackAPIException;
	
}
