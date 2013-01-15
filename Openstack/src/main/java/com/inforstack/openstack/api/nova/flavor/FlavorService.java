package com.inforstack.openstack.api.nova.flavor;

import com.inforstack.openstack.api.OpenstackAPIException;

public interface FlavorService {

	public final static String ENDPOINT_FLAVORS			= "openstack.endpoint.flavors";
	public final static String ENDPOINT_FLAVORS_DETAIL	= "openstack.endpoint.flavors.detail";
	public final static String ENDPOINT_FLAVOR			= "openstack.endpoint.flavor";
	public final static String CACHE_EXPIRE				= "openstack.cache.expire";
	
	public Flavor[] listFlavors() throws OpenstackAPIException;
	
	public Flavor getFlavor(String id) throws OpenstackAPIException;
	
	public Flavor createFlavor(String name, int vcpus, int ram, int disk) throws OpenstackAPIException;
	
	public void removeFlavor(Flavor flavor) throws OpenstackAPIException;
	
}
