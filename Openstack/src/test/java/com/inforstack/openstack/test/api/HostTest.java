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
import com.inforstack.openstack.api.host.HostDescribe;
import com.inforstack.openstack.api.host.HostDescribes;
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
				System.out.println("Zone:     " + host.getZone());
				System.out.println("Name:     " + host.getName());
				System.out.println("Service:  " + host.getService());
			}
		} catch (OpenstackAPIException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetHostDescrible() {
		try {
			Host[] hosts = this.hostService.getHosts();
			if (hosts.length > 0) {
				Host host = hosts[0];
				HostDescribes describes = this.hostService.getHostDescribe(host);
				Assert.assertNotNull(describes);
				Assert.assertTrue(describes.getHost().length > 0);
				for (HostDescribe describe : describes.getHost()) {
					HostDescribe.ProjectResource resource = describe.getResource();
					System.out.println("#######");
					System.out.println("Project    :" + resource.getProject());
					System.out.println("CPU        :" + resource.getCpu());
					System.out.println("Memory(mb) :" + resource.getMemory());
					System.out.println("Disk(gb)   :" + resource.getDisk());
				}
			}
		} catch (OpenstackAPIException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

}
