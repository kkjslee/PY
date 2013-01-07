package com.inforstack.openstack.api.nova.image;

import com.inforstack.openstack.api.OpenstackAPIException;

public interface ImageService {
	
	public final static String ENDPOINT_IMAGES			= "openstack.endpoint.images";
	
	public Image[] listImages() throws OpenstackAPIException;

}
