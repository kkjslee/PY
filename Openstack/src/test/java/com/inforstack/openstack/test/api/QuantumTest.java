package com.inforstack.openstack.test.api;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.quantum.Network;
import com.inforstack.openstack.api.quantum.Port;
import com.inforstack.openstack.api.quantum.Port.IP;
import com.inforstack.openstack.api.quantum.QuantumService;
import com.inforstack.openstack.api.quantum.Subnet;
import com.inforstack.openstack.configuration.ConfigurationDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
public class QuantumTest {
	
	@Autowired
	private QuantumService quantumService;
	
	@Autowired
	private KeystoneService keystoneService;
	
	@Autowired
	private ConfigurationDao configurationDao;
	
	private Access access;

	@Before
	public void setUp() throws Exception {
		String tenant = this.configurationDao.findByName(KeystoneService.TENANT_DEMO_ID).getValue();
		String username = this.configurationDao.findByName(KeystoneService.USER_ADMIN_NAME).getValue();
		String password = this.configurationDao.findByName(KeystoneService.USER_ADMIN_PASS).getValue();
		this.access = this.keystoneService.getAccess(username, password, tenant, true);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testListNetworksAndSubnets() {
		try {
			Network[] networks = this.quantumService.listNetworks(this.access);
			Assert.assertNotNull(networks);
			Assert.assertTrue(networks.length > 0);
			System.out.println("\n\n\n");
			System.out.println("======= Networks =======");
			for (Network network : networks) {
				System.out.println("------------------------");
				System.out.println("ID:           " + network.getId());
				System.out.println("Name:         " + network.getName());
				System.out.println("Tenant:       " + network.getTenant());
				System.out.println("AdminStateUp: " + network.isAdminStateUp());
				System.out.println("Shared:       " + network.isShared());
				String[] subnets = network.getSubnets();
				System.out.println("Subnet:       " + (subnets != null ? subnets.length : 0));
				for (String subnetId : subnets) {
					Subnet subnet = this.quantumService.getSubnet(access, subnetId);
					System.out.println("--------------------------");
					System.out.println("ID:           " + subnet.getId());
					System.out.println("Name:         " + subnet.getName());
					System.out.println("IP Ver:       " + subnet.getIpVersion());
					System.out.println("Gateway:      " + subnet.getGateway());
					System.out.println("CIDR:         " + subnet.getCidr());
					System.out.println("DHCP:         " + subnet.isDhcp());
				}
				System.out.println("\n");
				System.out.println("Status:       " + network.getStatus());
				System.out.println("\n\n");
			}
		} catch (OpenstackAPIException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testListPorts() {
		try {
			Port[] ports = this.quantumService.listPorts(this.access);
			Assert.assertNotNull(ports);
			Assert.assertTrue(ports.length > 0);
			System.out.println("\n\n\n");
			System.out.println("======= Ports =======");
			for (Port port : ports) {
				System.out.println("---------------------");
				System.out.println("ID:           " + port.getId());
				System.out.println("Name:         " + port.getName());
				System.out.println("Tenant:       " + port.getTenant());
				System.out.println("AdminStateUp: " + port.isAdminStateUp());
				System.out.println("Device:       " + port.getDevice());
				System.out.println("MAC:          " + port.isAdminStateUp());
				IP[] subnets = port.getIps();
				System.out.println("Fixed IP:     " + (subnets != null ? subnets.length : 0));
				for (IP ip : subnets) {
					System.out.println("---------------------");
					System.out.println("Subnet:       " + ip.getSubnet());
					System.out.println("IP:           " + ip.getIp());
				}
				System.out.println("Status:       " + port.getStatus());
				System.out.println("\n\n");
			}
		} catch (OpenstackAPIException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

}
