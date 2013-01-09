package com.inforstack.openstack.api.nova.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.RequestBody;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.nova.flavor.Flavor;
import com.inforstack.openstack.api.nova.flavor.FlavorService;
import com.inforstack.openstack.api.nova.image.Image;
import com.inforstack.openstack.api.nova.image.ImageService;
import com.inforstack.openstack.api.nova.server.Server;
import com.inforstack.openstack.api.nova.server.ServerAction;
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
	
	@Autowired
	private FlavorService flavorService;
	
	@Autowired
	private ImageService imageService;
	
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
	public Server[] listServers(Access access, boolean flavorAndImage) throws OpenstackAPIException {
		Server[] servers = null;
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SERVERS_DETAIL);
		if (endpoint != null) {
			Servers response = RestUtils.get(endpoint.getValue(), access, Servers.class, access.getToken().getTenant().getId());
			if (response != null) {
				servers = response.getServers();
				for (Server server : servers) {
					if (flavorAndImage) {
						Flavor flavor = this.flavorService.getFlavor(server.getFlavor().getId());
						server.setFlavor(flavor);
						Image image = this.imageService.getImage(server.getImage().getId());
						server.setImage(image);
					}
				}
			}
		}
		return servers;
	}
	
	public static final class ServerBody implements RequestBody {
		
		private Server server;

		public Server getServer() {
			return server;
		}

		public void setServer(Server server) {
			this.server = server;
		}
		
	}
	
	@Override
	public Server getServer(Access access, String id, boolean flavorAndImage) throws OpenstackAPIException {
		Server server = null;
		Configuration endpointServer = this.configurationDao.findByName(ENDPOINT_SERVER);
		if (access != null && endpointServer != null) {
			ServerBody response = RestUtils.get(endpointServer.getValue(), access, ServerBody.class, access.getToken().getTenant().getId(), id);
			server = response.getServer();
			if (flavorAndImage) {
				Flavor flavor = this.flavorService.getFlavor(server.getFlavor().getId());
				server.setFlavor(flavor);
				Image image = this.imageService.getImage(server.getImage().getId());
				server.setImage(image);
			}
		}
		return server;
	}

	@Override
	public Server createServer(Access access, Server server) throws OpenstackAPIException {
		Server newServer = null;
		Configuration endpointServers = this.configurationDao.findByName(ENDPOINT_SERVERS);
		if (access != null && endpointServers != null) {
			ServerBody request = new ServerBody();
			request.setServer(server);
			ServerBody response = RestUtils.postForObject(endpointServers.getValue(), access, request, ServerBody.class, access.getToken().getTenant().getId());
			newServer = response.getServer();
			newServer = this.getServer(access, newServer.getId(), true);
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

	@Override
	public void doServerAction(Access access, Server server, ServerAction action) throws OpenstackAPIException {
		if (access != null && server != null && !server.getId().trim().isEmpty()) {
			Configuration endpointUser = this.configurationDao.findByName(ENDPOINT_SERVER_ACTION);
			if (endpointUser != null) {
				RestUtils.postForLocation(endpointUser.getValue(), access, action, access.getToken().getTenant().getId(), server.getId());
			}
		}
	}

}
