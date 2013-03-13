package com.inforstack.openstack.network.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.quantum.QuantumService;
import com.inforstack.openstack.item.DataCenter;
import com.inforstack.openstack.network.Network;
import com.inforstack.openstack.network.NetworkDao;
import com.inforstack.openstack.network.NetworkService;
import com.inforstack.openstack.network.Subnet;
import com.inforstack.openstack.network.SubnetDao;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;
import com.inforstack.openstack.utils.OpenstackUtil;

@Service
@Transactional
public class NetworkServiceImpl implements NetworkService {
	
	@Autowired
	private NetworkDao networkDao;
	
	@Autowired
	private SubnetDao subnetDao;
	
	@Autowired
	private KeystoneService keystoneService;
	
	@Autowired
	private QuantumService quantumService;
	
	@Autowired
	private UserService userService;

	@Override
	public List<Network> listNetworks() {
		return this.networkDao.listAll();
	}

	@Override
	public List<Network> listNetworksFromTenant(Tenant tenant, Integer dataCenterId) {
		return this.networkDao.listNetworks(tenant, dataCenterId);
	}

	@Override
	public List<Subnet> listSubnets() {
		return this.subnetDao.listAll();
	}

	@Override
	public List<Subnet> listSubnetsFromTenant(Tenant tenant) {
		return this.subnetDao.listSubnets(tenant, null, null);
	}

	@Override
	public List<Subnet> listSubnetsFromNetwork(Network network) {
		return this.subnetDao.listSubnets(null, network, null);
	}

	@Override
	public Network createNetwork(User user, Tenant tenant, String name, DataCenter dataCenter) {
		Network network = null;
		Access access = null;
		try {
			access = this.keystoneService.getAccess(user.getUsername(), this.userService.getOpenstackUserPassword(), tenant.getUuid(), true);
			if (access != null) {
				com.inforstack.openstack.api.quantum.Network osNetwork = this.quantumService.createNetwork(access, name, true, false, false);
				if (osNetwork != null && osNetwork.getStatus().equalsIgnoreCase("ACTIVE")) {
					network = new Network();
					network.setName(name);
					network.setUuid(osNetwork.getId());
					network.setTenant(tenant);
					network.setDataCenter(dataCenter);
					this.networkDao.persist(network);
					
					NetworkService self = (NetworkService) OpenstackUtil.getBean(NetworkService.class);
					self.createSubnet(user, tenant, network, name + "_sub", dataCenter);
				}
			}
		} catch (OpenstackAPIException e) {
			throw new RuntimeException(e);
		}
		return network;
	}

	@Override
	public void removeNetwork(User user, Tenant tenant, String uuid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Subnet createSubnet(User user, Tenant tenant, Network network, String name, DataCenter dataCenter) {
		Subnet subnet = null;
		Access access = null;
		try {
			access = this.keystoneService.getAccess(user.getUsername(), this.userService.getOpenstackUserPassword(), tenant.getUuid(), true);
			if (access != null) {
				NetworkService self = (NetworkService) OpenstackUtil.getBean(NetworkService.class);
				int[] address = self.generateAddress(dataCenter);
				String cidr = "" + address[0] + "." + address[1] + "." + address[2] + ".0/24";
				com.inforstack.openstack.api.quantum.Subnet osSubnet = this.quantumService.createSubnet(access, network.getUuid(), name, 4, cidr, null);
				com.inforstack.openstack.api.quantum.Router osRouter = this.quantumService.createRouter(access, tenant.getName() + "_router", dataCenter.getExternalNet());
				this.quantumService.addInterface(access, osRouter, osSubnet);
				if (osSubnet != null && !osSubnet.getId().isEmpty()) {
					subnet = new Subnet();
					subnet.setUuid(osSubnet.getId());
					subnet.setName(name);
					subnet.setA1(address[0]);
					subnet.setA2(address[1]);
					subnet.setA3(address[2]);
					subnet.setNetwork(network);
					subnet.setTenant(tenant);
					subnet.setDataCenter(dataCenter);
					this.subnetDao.persist(subnet);
				}
			}
		} catch (OpenstackAPIException e) {
			throw new RuntimeException(e);
		}
		return subnet;
	}

	@Override
	public void removeSubnet(User user, Tenant tenant, String uuid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] generateAddress(DataCenter dataCenter) {
		int[] address = new int[3];
		List<Subnet> list = this.subnetDao.listSubnets(null, null, dataCenter.getId());
		int size = list.size();
		int n = 0;
		for (int i = 10; i < 11 && n <= size; i++) {
			address[0] = i;
			for (int j = 5; j < 256 && n <= size; j++) {
				address[1] = j;
				for (int k = 0; k < 256 && n <= size; k++) {
					address[2] = k;
					n++;
				}
			}
		}
		return address;
	}
	
	

}
