package com.inforstack.openstack.test.api;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.host.Host;
import com.inforstack.openstack.api.host.HostService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
public class HostTest {
	
	@Autowired
	private HostService hostService;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetHosts() {
		try {
			Host[] hosts = this.hostService.getHosts();
			Assert.assertNotNull(hosts);
			Assert.assertTrue(hosts.length > 0);
			for (Host host : hosts) {
				System.out.println("#######");
				System.out.println("Zone:\t\t" + host.getZone());
				System.out.println("Name:\t\t" + host.getHost_name());
				System.out.println("Service:\t" + host.getService());
			}
		} catch (OpenstackAPIException e) {
			Assert.fail();
		}
		
	}

}
