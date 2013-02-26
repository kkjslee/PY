package com.inforstack.openstack.api.nova.server.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.RequestBody;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.Access.Service.EndPoint.Type;
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
import com.inforstack.openstack.instance.Instance;
import com.inforstack.openstack.instance.InstanceDao;
import com.inforstack.openstack.instance.InstanceStatus;
import com.inforstack.openstack.instance.InstanceStatusDao;
import com.inforstack.openstack.instance.UserActionDao;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.RestUtils;

@Service("serverService")
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
	
	@Autowired
	private InstanceDao instanceDao;
	
	@Autowired
	private InstanceStatusDao instanceStatusDao;
	
	@Autowired
	private UserActionDao userActionDao;
	
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
			String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
			Servers response = RestUtils.get(url, access, Servers.class);
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
			String url = getEndpoint(access, Type.INTERNAL, endpointServer.getValue());
			try {
				ServerBody response = RestUtils.get(url, access, ServerBody.class, id);
				server = response.getServer();
				if (flavorAndImage) {
					Flavor flavor = this.flavorService.getFlavor(server.getFlavor().getId());
					server.setFlavor(flavor);
					Image image = this.imageService.getImage(server.getImage().getId());
					server.setImage(image);
				}
			} catch (OpenstackAPIException e) {
				RestUtils.handleError(e);
			}
		}
		return server;
	}

	@Override
	public Server createServer(final Access access, Server server) throws OpenstackAPIException {
		Server newServer = null;
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SERVERS);
		if (access != null && endpoint != null) {
			String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
			ServerBody request = new ServerBody();
			request.setServer(server);
			ServerBody response = RestUtils.postForObject(url, access, request, ServerBody.class);
			newServer = response.getServer();
			newServer = this.getServer(access, newServer.getId(), true);
			
			final String id = newServer.getId();
			
			final ServerService self = (ServerService) OpenstackUtil.getBean("serverService");
			
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						try {
							Server s = ServerServiceImpl.this.getServerDetail(access, id);
							if (s != null) {
								String status = s.getStatus();
								String task = s.getTask();
								self.updateServerStatus(s.getId(), status, task);
								if (status.equalsIgnoreCase("active") || status.equalsIgnoreCase("error")) {
									break;
								}
							} else {
								break;
							}
							Thread.sleep(1000);
						} catch (OpenstackAPIException e) {
							break;
						} catch (InterruptedException e) {
							break;
						}
					}
				}
				
			}, "Creating server " + server.getId());
			thread.start();
		}
		return newServer;
	}

	@Override
	public void removeServer(final Access access, final Server server) throws OpenstackAPIException {
		if (access != null && server != null && !server.getId().trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SERVER);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				RestUtils.delete(url, access, server.getId());
				
				final ServerService self = (ServerService) OpenstackUtil.getBean("serverService");
				
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						while (true) {
							try {
								Server s = ServerServiceImpl.this.getServerDetail(access, server.getId());
								if (s != null) {
									String status = s.getStatus();
									String task = s.getTask();
									self.updateServerStatus(s.getId(), status, task);
									if (status.equalsIgnoreCase("error")) {
										break;
									}
								} else {
									self.updateServerStatus(server.getId(), "deleted", null);
									break;
								}
								Thread.sleep(1000);
							} catch (OpenstackAPIException e) {
								break;
							} catch (InterruptedException e) {
								break;
							}
						}
					}
					
				}, "Removing server " + server.getId());
				thread.start();
			}
		}
	}

	@Override
	public void doServerAction(final Access access, final Server server, ServerAction action) throws OpenstackAPIException {
		if (access != null && server != null && !server.getId().trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SERVER_ACTION);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				RestUtils.postForLocation(url, access, action, server.getId());
				
				final ServerService self = (ServerService) OpenstackUtil.getBean("serverService");
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						while (true) {
							try {
								Server s = ServerServiceImpl.this.getServerDetail(access, server.getId());
								if (s != null) {
									String status = s.getStatus();
									String task = s.getTask();
									self.updateServerStatus(s.getId(), status, task);
									if (task == null || task.equalsIgnoreCase("none")) {
										break;
									}
								} else {
									break;
								}
								Thread.sleep(1000);
							} catch (OpenstackAPIException e) {
								break;
							} catch (InterruptedException e) {
								break;
							}
						}
					}
					
				}, "Updating server " + server.getId());
				thread.start();
			}
		}
	}
	
	@Override
	public void updateServerStatus(String uuid, String status, String task) {
		Instance instance = ServerServiceImpl.this.instanceDao.findByObject("uuid", uuid);
		if (instance != null) {
			Date now = new Date();
			String oldStatus = instance.getStatus();
			if (!oldStatus.equals(status)) {
				InstanceStatus instanceStatus = new InstanceStatus();
				instanceStatus.setUuid(uuid);
				instanceStatus.setStatus(status);
				instanceStatus.setUpdateTime(now);				
				this.instanceStatusDao.persist(instanceStatus);
			}
			instance.setStatus(status);
			instance.setTask(task);
			instance.setUpdateTime(now);
		}
	}
	
	private Server getServerDetail(Access access, String id) throws OpenstackAPIException {
		Server server = null;
		Configuration endpointServer = this.configurationDao.findByName(ENDPOINT_SERVER);
		if (access != null && endpointServer != null) {
			String url = getEndpoint(access, Type.INTERNAL, endpointServer.getValue());
			try {
				ServerBody response = RestUtils.get(url, access, ServerBody.class, id);
				server = response.getServer();
			} catch (OpenstackAPIException e) {
				RestUtils.handleError(e);
			}
		}
		return server;
	}
	
	private static String getEndpoint(Access access, Type type, String suffix) {
		return RestUtils.getEndpoint(access, "nova", type, suffix);
	}

}
