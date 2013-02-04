package com.inforstack.openstack.api.quantum.impl;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.RequestBody;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.Access.Service.EndPoint.Type;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.quantum.Network;
import com.inforstack.openstack.api.quantum.Port;
import com.inforstack.openstack.api.quantum.QuantumService;
import com.inforstack.openstack.api.quantum.Subnet;
import com.inforstack.openstack.configuration.Configuration;
import com.inforstack.openstack.configuration.ConfigurationDao;
import com.inforstack.openstack.utils.RestUtils;

@Service
@Transactional
public class QuantumServiceImpl implements QuantumService {
	
	@Autowired
	private ConfigurationDao configurationDao;
	
	@Autowired
	private KeystoneService tokenService;
	
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
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_NETWORKS);
		if (endpoint != null) {
			String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
			Networks response = RestUtils.get(url, access, Networks.class);
			if (response != null) {
				networks = response.getNetworks();
			}
		}
		return networks;
	}
	
	@Override
	public Network getNetwork(Access access, String id) throws OpenstackAPIException {
		Network network = null;
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_NETWORK);
		if (endpoint != null) {
			String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
			NetworkBody response = RestUtils.get(url, access, NetworkBody.class, id);
			if (response != null) {
				network = response.getNetwork();
			}
		}
		return network;
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
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SUBNETS);
		if (endpoint != null) {
			String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
			Subnets response = RestUtils.get(url, access, Subnets.class);
			if (response != null) {
				subnets = response.getSubnets();
			}
		}
		return subnets;
	}	
	
	@Override
	public Subnet getSubnet(Access access, String id) throws OpenstackAPIException {
		Subnet subnet = null;
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SUBNET);
		if (endpoint != null) {
			String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
			SubnetBody response = RestUtils.get(url, access, SubnetBody.class, id);
			if (response != null) {
				subnet = response.getSubnet();
			}
		}
		return subnet;
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
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_PORTS);
		if (endpoint != null) {
			String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
			Ports response = RestUtils.get(url, access, Ports.class);
			if (response != null) {
				ports = response.getPorts();
			}
		}
		return ports;
	}
	
	@Override
	public Port getPort(Access access, String id) throws OpenstackAPIException {
		Port port = null;
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_PORT);
		if (endpoint != null) {
			String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
			PortBody response = RestUtils.get(url, access, PortBody.class, id);
			if (response != null) {
				port = response.getPort();
			}
		}
		return port;
	}
	
	private static String getEndpoint(Access access, Type type, String suffix) {
		return RestUtils.getEndpoint(access, "quantum", type, suffix);
	}

}
