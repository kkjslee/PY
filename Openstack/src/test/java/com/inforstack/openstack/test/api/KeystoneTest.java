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
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.keystone.Tenant;
import com.inforstack.openstack.api.keystone.User;
import com.inforstack.openstack.configuration.ConfigurationDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
public class KeystoneTest {

	@Autowired
	private KeystoneService keystoneService;
	
	@Autowired
	private ConfigurationDao configurationDao;
	
	private String tenant;
	
	private String username;
	
	private String password;
	
	@Before
	public void setUp() throws Exception {
		this.tenant = this.configurationDao.findByName(KeystoneService.TENANT_ADMIN_ID).getValue();
		this.username = this.configurationDao.findByName(KeystoneService.USER_ADMIN_NAME).getValue();
		this.password = this.configurationDao.findByName(KeystoneService.USER_ADMIN_PASS).getValue();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetToken() {
		try {
			Access access = this.keystoneService.getAccess(this.username, this.password, tenant, false);
			if (access == null) {
				access = this.keystoneService.getAccess(this.username, this.password, tenant, true);
			}
			Assert.assertNotNull(access);
			Assert.assertFalse(this.keystoneService.isExpired(access));
		} catch (OpenstackAPIException e) {
			Assert.fail("Could not get access");
			e.printStackTrace();
		}
	}

	@Test
	public void testApplyToken() {
		try {
			Access access = this.keystoneService.applyAccess(this.username, this.password, tenant);
			Assert.assertNotNull(access);
			Assert.assertFalse(this.keystoneService.isExpired(access));
		} catch (OpenstackAPIException e) {
			Assert.fail("Could not apply new access");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTenants() {
		try {
			Tenant[] tenants = this.keystoneService.listTenants();
			Assert.assertNotNull(tenants);
			Assert.assertTrue(tenants.length > 0);
			System.out.println("\n\n\n");
			System.out.println("======= Tenants =======");
			for (Tenant tenant : tenants) {
				System.out.println("-----------------------");
				System.out.println("ID:           " + tenant.getId());
				System.out.println("Name:         " + tenant.getName());
				System.out.println("Enable:       " + tenant.isEnabled());
				System.out.println("Description:  " + tenant.getDescription());
			}
		} catch (OpenstackAPIException e) {
			Assert.fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTenantAction() {
		try {
			Tenant[] tenants = this.keystoneService.listTenants();
			int size = tenants.length;
			Tenant testTenant = this.keystoneService.addTenant("test", "test", true);
			Assert.assertNotNull(testTenant);
			Assert.assertNotNull(testTenant.getId());
			Assert.assertFalse(testTenant.getId().isEmpty());
			testTenant.setName("update");
			testTenant = this.keystoneService.updateTenant(testTenant);
			Assert.assertTrue(testTenant.getName().equals("update"));
			Assert.assertTrue(this.keystoneService.listTenants().length == size + 1);
			this.keystoneService.removeTenant(testTenant);
			Assert.assertTrue(this.keystoneService.listTenants().length == size);
		} catch (OpenstackAPIException e) {
			Assert.fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUserAction() {
		try {
			User testUser = this.keystoneService.addUser("user", "pass", "test@email.com");
			Assert.assertNotNull(testUser);
			Assert.assertNotNull(testUser.getId());
			Assert.assertFalse(testUser.getId().isEmpty());
//			testUser.setName("update");
//			testUser = this.keystoneService.updateUser(testUser);
//			Assert.assertTrue(testUser.getName().equals("update"));
			this.keystoneService.removeUser(testUser);
		} catch (OpenstackAPIException e) {
			Assert.fail();
			e.printStackTrace();
		}
	}

}
