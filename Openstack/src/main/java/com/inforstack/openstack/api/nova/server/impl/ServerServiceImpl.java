package com.inforstack.openstack.api.nova.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.nova.server.Server;
import com.inforstack.openstack.api.nova.server.ServerService;
import com.inforstack.openstack.configuration.Configuration;
import com.inforstack.openstack.configuration.ConfigurationDao;
import com.inforstack.openstack.utils.RestUtils;

@Service
@Transactional
public class ServerServiceImpl implements ServerService {

	@Autowired
	private ConfigurationDao configurationDao;
	
	@Autowired
	private KeystoneService tokenService;
	
	public static final class Servers {
		
		private Server[] servers;

		public Server[] getServers() {
			return servers;
		}

		public void setServers(Server[] servers) {
			this.servers = servers;
		}
		
	}
	
	@Override
	public Server[] listServers(String tenant) throws OpenstackAPIException {
		Server[] servers = null;
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SERVERS_DETAIL);
		if (endpoint != null) {
			Access access = this.tokenService.getAdminAccess();
			Servers response = RestUtils.get(endpoint.getValue(), access, Servers.class, tenant);
			if (response != null) {
				servers = response.getServers();
			}
		}
		return servers;
	}

}
