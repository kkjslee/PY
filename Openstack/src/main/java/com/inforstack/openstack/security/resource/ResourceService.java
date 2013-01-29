package com.inforstack.openstack.security.resource;

import java.util.List;



public interface ResourceService {

	/**
	 * list all resources
	 * @return
	 */
	public List<Resource> listAll();

}
