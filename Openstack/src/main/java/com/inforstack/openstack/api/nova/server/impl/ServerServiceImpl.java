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
	public Server[] listServers(Access access) throws OpenstackAPIException {
		Server[] servers = null;
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SERVERS_DETAIL);
		if (endpoint != null) {
			Servers response = RestUtils.get(endpoint.getValue(), access, Servers.class, access.getToken().getTenant().getId());
			if (response != null) {
				servers = response.getServers();
			}
		}
		return servers;
	}
	
	public static final class ServerBody {
		
		private Server server;

		public Server getServer() {
			return server;
		}

		public void setServer(Server server) {
			this.server = server;
		}
		
	}

	@Override
	public Server createServer(Access access, Server server) throws OpenstackAPIException {
		Server newServer = null;
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SERVERS);
		if (access != null && endpoint != null) {
			ServerBody request = new ServerBody();
			request.setServer(server);
			ServerBody response = RestUtils.post(endpoint.getValue(), access, request, ServerBody.class, access.getToken().getTenant().getId());
			newServer = response.getServer();
		}
		return newServer;
	}

	@Override
	public void removeServer(Access access, Server server) throws OpenstackAPIException {
		if (access != null && server != null && !server.getId().trim().isEmpty()) {
			Configuration endpointUser = this.configurationDao.findByName(ENDPOINT_SERVER);
			if (endpointUser != null) {
				RestUtils.delete(endpointUser.getValue(), access, access.getToken().getTenant().getId(), server.getId());
			}
		}
	}

}
