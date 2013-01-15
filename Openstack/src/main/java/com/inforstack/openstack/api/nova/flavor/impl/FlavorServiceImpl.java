package com.inforstack.openstack.api.nova.flavor.impl;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.RequestBody;
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
	
	private static Flavor[] cache = null;
	
	private static Date update = null;
	
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
		Date now = new Date();
		if (cache == null || update == null || now.after(update)) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_FLAVORS_DETAIL);
			if (endpoint != null) {
				Access access = this.tokenService.getAdminAccess();
				Flavors response = RestUtils.get(endpoint.getValue(), access, Flavors.class, access.getToken().getTenant().getId());
				if (response != null) {
					flavors = response.getFlavors();
					Configuration expire = this.configurationDao.findByName(CACHE_EXPIRE);
					if (expire != null) {
						cache = flavors;
						now.setTime(now.getTime() + Integer.parseInt(expire.getValue()) * 60 * 1000);
						update = now;
					}
				}
			}
		} else {
			flavors = cache;
		}
		return flavors;
	}

	@Override
	public Flavor getFlavor(String id) throws OpenstackAPIException {
		Flavor flavor = null;
		Flavor[] flavors = this.listFlavors();
		if (flavors != null) {
			for (Flavor f : flavors) {
				if (f.getId().equalsIgnoreCase(id)) {
					flavor = f;
					break;
				}
			}
		}
		return flavor;
	}
	
	private static final class FlavorBody implements RequestBody {
		
		@JsonProperty("flavor")
		private Flavor flavor;
		
		public Flavor getFlavor() {
			return this.flavor;
		}
		
		public void setFlavor(Flavor flavor) {
			this.flavor = flavor;
		}
		
	}
	
	@Override
	public Flavor createFlavor(String name, int vcpus, int ram, int disk) throws OpenstackAPIException {
		Flavor flavor = null;
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_FLAVORS);
		if (endpoint != null) {
			Access access = this.tokenService.getAdminAccess();
			if (access != null) {
				String id = java.util.UUID.randomUUID().toString();
				Flavor newFlavor = new Flavor();
				newFlavor.setId(id);
				newFlavor.setName(name);
				newFlavor.setVcpus(vcpus);
				newFlavor.setRam(ram);
				newFlavor.setDisk(disk);
				newFlavor.setFactor(1);
				
				FlavorBody request = new FlavorBody();
				request.setFlavor(newFlavor);
				
				FlavorBody response = RestUtils.postForObject(endpoint.getValue(), access, request, FlavorBody.class, access.getToken().getTenant().getId());
				if (response != null) {
					flavor = response.getFlavor();
					cache = null;
				}
			}
		}
		return flavor;
	}
	
	@Override
	public void removeFlavor(Flavor flavor) throws OpenstackAPIException {
		if (flavor != null && !flavor.getId().trim().isEmpty()) {
			Access access = this.tokenService.getAdminAccess();
			if (access != null) {
				Configuration endpointUser = this.configurationDao.findByName(ENDPOINT_FLAVOR);
				if (endpointUser != null) {
					RestUtils.delete(endpointUser.getValue(), access, access.getToken().getTenant().getId(), flavor.getId());
					cache = null;
				}
			}
		}
	}

}
