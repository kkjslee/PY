package com.inforstack.openstack.api.quantum.impl;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.RequestBody;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.Access.Service.EndPoint.Type;
import com.inforstack.openstack.api.nova.server.Server;
import com.inforstack.openstack.api.quantum.FloatingIP;
import com.inforstack.openstack.api.quantum.Network;
import com.inforstack.openstack.api.quantum.Port;
import com.inforstack.openstack.api.quantum.QuantumService;
import com.inforstack.openstack.api.quantum.Router;
import com.inforstack.openstack.api.quantum.Router.Gateway;
import com.inforstack.openstack.api.quantum.Subnet;
import com.inforstack.openstack.api.quantum.Subnet.AllocationPool;
import com.inforstack.openstack.configuration.Configuration;
import com.inforstack.openstack.configuration.ConfigurationDao;
import com.inforstack.openstack.instance.Instance;
import com.inforstack.openstack.instance.InstanceDao;
import com.inforstack.openstack.instance.InstanceStatus;
import com.inforstack.openstack.instance.InstanceStatusDao;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.RestUtils;

@Service
@Transactional
public class QuantumServiceImpl implements QuantumService {

	@Autowired
	private ConfigurationDao configurationDao;
	
	@Autowired
	private InstanceDao instanceDao;
	
	@Autowired
	private InstanceStatusDao instanceStatusDao;

	public static final class Networks {

		private Network[] networks;

		public Network[] getNetworks() {
			return networks;
		}

		public void setNetworks(Network[] networks) {
			this.networks = networks;
		}

	}

	private static final class NetworkBody implements RequestBody {

		@JsonProperty("network")
		private Network network;

		public Network getNetwork() {
			return this.network;
		}

		public void setNetwork(Network network) {
			this.network = network;
		}

	}

	@Override
	public Network[] listNetworks(Access access) throws OpenstackAPIException {
		Network[] networks = null;
		if (access != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_NETWORKS);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				Networks response = RestUtils.get(url, access, Networks.class);
				if (response != null) {
					networks = response.getNetworks();
				}
			}
		}
		return networks;
	}

	@Override
	public Network getNetwork(Access access, String id) throws OpenstackAPIException {
		Network network = null;
		if (access != null && !id.trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_NETWORK);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				try {
					NetworkBody response = RestUtils.get(url, access, NetworkBody.class, id);
					if (response != null) {
						network = response.getNetwork();
					}
				} catch (OpenstackAPIException e) {
					RestUtils.handleError(e);
				}
			}
		}
		return network;
	}

	@Override
	public Network createNetwork(Access access, String name, boolean adminStateUp, boolean shared, boolean external) throws OpenstackAPIException {
		Network network = null;
		if (access != null && !name.trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_NETWORKS);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				Network networkInstance = new Network();
				networkInstance.setName(name);
				networkInstance.setAdminStateUp(adminStateUp);
				networkInstance.setShared(shared);
				networkInstance.setExternal(external);
				NetworkBody request = new NetworkBody();
				request.setNetwork(networkInstance);
				NetworkBody response = RestUtils.postForObject(url, access, request, NetworkBody.class);
				if (response != null) {
					network = response.getNetwork();
				}
			}
		}
		return network;
	}

	@Override
	public void updateNetwork(Access access, Network network, String name, boolean adminStateUp, boolean shared, boolean external) throws OpenstackAPIException {
		if (access != null && network != null && !network.getId().trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_NETWORK);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				Network networkInstance = new Network();
				networkInstance.setName(name);
				networkInstance.setAdminStateUp(adminStateUp);
				networkInstance.setShared(shared);
				networkInstance.setExternal(external);
				NetworkBody request = new NetworkBody();
				request.setNetwork(networkInstance);
				RestUtils.put(url, access, request, network.getId());
				network.setName(name);
				network.setAdminStateUp(adminStateUp);
				network.setShared(shared);
				network.setExternal(external);
			}
		}
	}

	@Override
	public void removeNetwork(Access access, String id) throws OpenstackAPIException {
		this.remove(access, ENDPOINT_NETWORK, id);
	}

	public static final class Subnets {

		private Subnet[] subnets;

		public Subnet[] getSubnets() {
			return subnets;
		}

		public void setSubnets(Subnet[] subnets) {
			this.subnets = subnets;
		}

	}

	private static final class SubnetBody implements RequestBody {

		@JsonProperty("subnet")
		private Subnet subnet;

		public Subnet getSubnet() {
			return this.subnet;
		}

		public void setSubnet(Subnet subnet) {
			this.subnet = subnet;
		}

	}

	@Override
	public Subnet[] listSubnets(Access access) throws OpenstackAPIException {
		Subnet[] subnets = null;
		if (access != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SUBNETS);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				Subnets response = RestUtils.get(url, access, Subnets.class);
				if (response != null) {
					subnets = response.getSubnets();
				}
			}
		}
		return subnets;
	}

	@Override
	public Subnet getSubnet(Access access, String id) throws OpenstackAPIException {
		Subnet subnet = null;
		if (access != null && !id.trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SUBNET);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				try {
					SubnetBody response = RestUtils.get(url, access, SubnetBody.class, id);
					if (response != null) {
						subnet = response.getSubnet();
					}
				} catch (OpenstackAPIException e) {
					RestUtils.handleError(e);
				}
			}
		}
		return subnet;
	}

	@Override
	public Subnet createSubnet(Access access, String network, String name, int ipVer, String cidr, AllocationPool[] pools) throws OpenstackAPIException {
		Subnet subnet = null;
		if (access != null && !network.trim().isEmpty() && (ipVer == 4 || ipVer == 6)) {
			if (this.getNetwork(access, network) != null) {
				Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SUBNETS);
				if (endpoint != null) {
					String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
					Subnet subnetInstance = new Subnet();
					subnetInstance.setNetwork(network);
					subnetInstance.setName(name);
					subnetInstance.setIpVersion(ipVer);
					subnetInstance.setCidr(cidr);
					subnetInstance.setPools(pools);
					SubnetBody request = new SubnetBody();
					request.setSubnet(subnetInstance);
					SubnetBody response = RestUtils.postForObject(url, access, request, SubnetBody.class);
					if (response != null) {
						subnet = response.getSubnet();
					}
				}
			}
		}
		return subnet;
	}

	@Override
	public void updateSubnet(Access access, Subnet subnet) throws OpenstackAPIException {
		if (access != null && subnet != null && !subnet.getId().trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SUBNET);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				SubnetBody request = new SubnetBody();
				request.setSubnet(subnet);
				RestUtils.put(url, access, request, subnet.getId());
			}
		}
	}

	@Override
	public void removeSubnet(Access access, String id) throws OpenstackAPIException {
		this.remove(access, ENDPOINT_SUBNET, id);
	}

	public static final class Ports {

		private Port[] ports;

		public Port[] getPorts() {
			return ports;
		}

		public void setPorts(Port[] ports) {
			this.ports = ports;
		}

	}

	private static final class PortBody implements RequestBody {

		@JsonProperty("port")
		private Port port;

		public Port getPort() {
			return this.port;
		}

		public void setPort(Port port) {
			this.port = port;
		}

	}

	@Override
	public Port[] listPorts(Access access) throws OpenstackAPIException {
		Port[] ports = null;
		if (access != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_PORTS);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				Ports response = RestUtils.get(url, access, Ports.class);
				if (response != null) {
					ports = response.getPorts();
				}
			}
		}
		return ports;
	}

	@Override
	public Port getPort(Access access, String id) throws OpenstackAPIException {
		Port port = null;
		if (access != null && !id.trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_PORT);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				try {
					PortBody response = RestUtils.get(url, access, PortBody.class, id);
					if (response != null) {
						port = response.getPort();
					}
				} catch (OpenstackAPIException e) {
					RestUtils.handleError(e);
				}
			}
		}
		return port;
	}

	@Override
	public Port createPort(Access access, Port port, Server server) throws OpenstackAPIException {
		Port newPort = null;
		if (access != null) {
			if (this.getNetwork(access, port.getNetwork()) != null) {
				Configuration endpoint = this.configurationDao.findByName(ENDPOINT_PORTS);
				if (endpoint != null) {
					String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
					PortBody request = new PortBody();
					request.setPort(port);
					PortBody response = RestUtils.postForObject(url, access, request, PortBody.class);
					if (response != null) {
						newPort = response.getPort();
					}
				}
			}
		}
		return newPort;
	}

	@Override
	public void removePort(Access access, String id) throws OpenstackAPIException {
		this.remove(access, ENDPOINT_PORT, id);
	}
	
	public static final class Routers {

		private Router[] routers;

		public Router[] getRouters() {
			return routers;
		}

		public void setRouters(Router[] routers) {
			this.routers = routers;
		}

	}

	private static final class RouterBody implements RequestBody {

		@JsonProperty("router")
		private Router router;

		public Router getRouter() {
			return this.router;
		}

		public void setRouter(Router router) {
			this.router = router;
		}

	}
	
	@Override
	public Router createRouter(Access access, String name, String external) throws OpenstackAPIException {
		Router newPort = null;
		if (access != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_ROUTERS);
			if (endpoint != null && external != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				Gateway gateway = new Gateway();
				gateway.setNetwork(external);
				Router router = new Router();
				router.setName(name);
				router.setAdminStateUp(true);
				router.setGateway(gateway);
				RouterBody request = new RouterBody();
				request.setRouter(router);
				RouterBody response = RestUtils.postForObject(url, access, request, RouterBody.class);
				if (response != null) {
					newPort = response.getRouter();
				}
			}
		}
		return newPort;
	}
	
	private static final class AddInterfaceBody implements RequestBody {

		@JsonProperty("subnet_id")
		private String subnet;

		public void setSubnet(String subnet) {
			this.subnet = subnet;
		}

	}
	
	@Override
	public void addInterface(Access access, Router router, Subnet subnet) throws OpenstackAPIException {
		if (access != null && router != null && subnet != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_ROUTER_INTERFACE_ADD);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				AddInterfaceBody request = new AddInterfaceBody();
				request.setSubnet(subnet.getId());
				RestUtils.put(url, access, request, router.getId());
			}
		}
	}
	
	private static final class FloatingIPBody implements RequestBody {

		@JsonProperty("floatingip")
		private FloatingIP floatingIP;

		public FloatingIP getFloatingIP() {
			return floatingIP;
		}

		public void setFloatingIP(FloatingIP floatingIP) {
			this.floatingIP = floatingIP;
		}

	}
	
	@Override
	public FloatingIP createFloatingIP(Access access, String external) throws OpenstackAPIException {
		FloatingIP floatingIP = null;
		if (access != null && external != null && !external.isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_FLOATINGIPS);
			if (endpoint != null && external != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				
				FloatingIP newFloatingIP = new FloatingIP();
				newFloatingIP.setFloatingNetwork(external);
				
				FloatingIPBody request = new FloatingIPBody();
				request.setFloatingIP(newFloatingIP);
				
				FloatingIPBody response = RestUtils.postForObject(url, access, request, FloatingIPBody.class);
				if (response != null) {
					floatingIP = response.getFloatingIP();
				}
			}
		}
		return floatingIP;
	}
	
	@Override
	public void associateFloatingIP(Access access, String server, String uuid) throws OpenstackAPIException {
		if (access != null && server != null) {
			QuantumService self = OpenstackUtil.getBean(QuantumService.class);
			Network[] networks = self.listNetworks(access);
			for (Network network : networks) {
				if (network.getName().startsWith(access.getToken().getTenant().getName()) && network.getName().endsWith("_private")) {
					Port[] ports = self.listPorts(access);
					for (Port port : ports) {
						if (port.getNetwork().equalsIgnoreCase(network.getId()) && port.getDevice().equalsIgnoreCase(server)) {
							Configuration endpoint = this.configurationDao.findByName(ENDPOINT_FLOATINGIP);
							if (endpoint != null) {
								String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
								FloatingIP newFloatingIP = new FloatingIP();
								newFloatingIP.setPort(port.getId());
								FloatingIPBody request = new FloatingIPBody();
								request.setFloatingIP(newFloatingIP);
								RestUtils.put(url, access, request, uuid);
								
								Instance instance = this.instanceDao.findByObject("uuid", uuid);
								instance.setStatus("in-use");
								this.instanceDao.persist(instance);
								InstanceStatus status = new InstanceStatus();
								status.setUuid(uuid);
								status.setStatus("associate");
								status.setUpdateTime(new Date());
								this.instanceStatusDao.persist(status);
							}
							break;
						}
					}
					break;
				}
			}
		}
	}
	
	private static final class DisassociateIPBody implements RequestBody {

		@JsonProperty("floatingip")
		private DisassociateIP floatingIP;

		public void setFloatingIP(DisassociateIP floatingIP) {
			this.floatingIP = floatingIP;
		}

	}
	
	private static final class DisassociateIP {	
		
		@JsonProperty("port_id")
		private Object port = null;
	}

	@Override
	public void disassociateFloatingIP(Access access, String uuid) throws OpenstackAPIException {
		if (access != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_FLOATINGIP);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				DisassociateIPBody request = new DisassociateIPBody();
				request.setFloatingIP(new DisassociateIP());
				RestUtils.put(url, access, request, uuid);
				
				Instance instance = this.instanceDao.findByObject("uuid", uuid);
				instance.setStatus("available");
				this.instanceDao.persist(instance);
				InstanceStatus status = new InstanceStatus();
				status.setUuid(uuid);
				status.setStatus("disassociate");
				status.setUpdateTime(new Date());
				this.instanceStatusDao.persist(status);
			}
		}
	}
	
	@Override
	public void removeFloatingIP(Access access, String id) throws OpenstackAPIException {
		this.remove(access, ENDPOINT_FLOATINGIP, id);
		Instance instance = this.instanceDao.findByObject("uuid", id);
		instance.setStatus("deleted");
		this.instanceDao.persist(instance);
		InstanceStatus status = new InstanceStatus();
		status.setUuid(id);
		status.setStatus("deleted");
		status.setUpdateTime(new Date());
		this.instanceStatusDao.persist(status);
	}

	private static String getEndpoint(Access access, Type type, String suffix) {
		return RestUtils.getEndpoint(access, "quantum", type, suffix);
	}

	private void remove(Access access, String urlName, String id) throws OpenstackAPIException {
		if (access != null && !id.trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(urlName);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				RestUtils.delete(url, access, id);
			}
		}
	}

}
