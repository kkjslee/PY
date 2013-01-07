package com.inforstack.openstack.api.nova.image;

import com.inforstack.openstack.api.OpenstackAPIException;

public interface ImageService {
	
	public final static String ENDPOINT_IMAGES_			= "openstack.endpoint.images";
	public final static String ENDPOINT_IMAGES_DETAIL	= "openstack.endpoint.images.detail";
	public final static String ENDPOINT_IMAGE			= "openstack.endpoint.image";
	
	public Image[] listImages() throws OpenstackAPIException;

}
