package com.inforstack.openstack.api.nova.flavor.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.nova.flavor.Flavor;
import com.inforstack.openstack.api.nova.flavor.FlavorService;
import com.inforstack.openstack.configuration.Configuration;
import com.inforstack.openstack.configuration.ConfigurationDao;
import com.inforstack.openstack.utils.RestUtils;

@Service
@Transactional
public class FlavorServiceImpl implements FlavorService {
	
	@Autowired
	private ConfigurationDao configurationDao;
	
	@Autowired
	private KeystoneService tokenService;
	
	public static final class Flavors {

		private Flavor[] flavors;

		public Flavor[] getFlavors() {
			return flavors;
		}

		public void setFlavors(Flavor[] flavors) {
			this.flavors = flavors;
		}
		
	}

	@Override
	public Flavor[] listFlavors() throws OpenstackAPIException {
		Flavor[] flavors = null;
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_FLAVORS);
		if (endpoint != null) {
			Access access = this.tokenService.getAdminAccess();
			Flavors response = RestUtils.get(endpoint.getValue(), access, Flavors.class, access.getToken().getTenant().getId());
			if (response != null) {
				flavors = response.getFlavors();
			}
		}
		return flavors;
	}

}
