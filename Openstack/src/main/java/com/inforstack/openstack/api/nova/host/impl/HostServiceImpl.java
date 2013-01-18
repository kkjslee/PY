package com.inforstack.openstack.api.nova.host.impl;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.keystone.Access.Service.EndPoint.Type;
import com.inforstack.openstack.api.nova.host.Host;
import com.inforstack.openstack.api.nova.host.HostDescribe;
import com.inforstack.openstack.api.nova.host.HostService;
import com.inforstack.openstack.configuration.Configuration;
import com.inforstack.openstack.configuration.ConfigurationDao;
import com.inforstack.openstack.utils.RestUtils;

@Service
@Transactional
public class HostServiceImpl implements HostService {

	@Autowired
	private ConfigurationDao configurationDao;
	
	@Autowired
	private KeystoneService tokenService;
	
	public static final class Hosts {

		private Host[] hosts;

		public Host[] getHosts() {
			return hosts;
		}

		public void setHosts(Host[] hosts) {
			this.hosts = hosts;
		}
		
	}
	
	@Override
	public Host[] listHosts() throws OpenstackAPIException {
		Host[] hosts = null;
		
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_HOSTS);
		if (endpoint != null) {
			Access access = this.tokenService.getAdminAccess();
			String url = getEndpoint(access, Type.ADMIN, endpoint.getValue());
			Hosts response = RestUtils.get(url, access, Hosts.class);
			if (response != null) {
				hosts = response.getHosts();
			}
		}
		return hosts;
	}

	public static final class HostDescribes {

		@JsonProperty("host")
		private HostDescribe[] describes;

		public HostDescribe[] getDescribes() {
			return describes;
		}

		public void setDescribes(HostDescribe[] describes) {
			this.describes = describes;
		}
		
	}
	
	@Override
	public HostDescribe[] getHostDescribes(Host host) throws OpenstackAPIException {
		HostDescribe[] describes = null;
		if (host != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_HOST_DESCRIBE);
			if (endpoint != null) {
				Access access = this.tokenService.getAdminAccess();
				String url = getEndpoint(access, Type.ADMIN, endpoint.getValue());
				describes = RestUtils.get(url, access, HostDescribes.class, host.getName()).getDescribes();
			}
		}
		return describes;
	}
	
	private static String getEndpoint(Access access, Type type, String suffix) {
		return RestUtils.getEndpoint(access, "nova", type, suffix);
	}

}
