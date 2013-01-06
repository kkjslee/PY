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
import com.inforstack.openstack.api.token.Access;
import com.inforstack.openstack.api.token.KeystoneService;
import com.inforstack.openstack.configuration.ConfigurationDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
public class TokenTest {

	@Autowired
	private KeystoneService tokenService;
	
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
			Access access = this.tokenService.getAccess(this.username, this.password, tenant, false);
			if (access == null) {
				access = this.tokenService.getAccess(this.username, this.password, tenant, true);
			}
			Assert.assertNotNull("Could not get access");
			Assert.assertFalse(this.tokenService.isExpired(access));
		} catch (OpenstackAPIException e) {
			Assert.fail("Could not get access");
		}
		
	}

	@Test
	public void testApplyToken() {
		try {
			Access access = this.tokenService.applyAccess(this.username, this.password, tenant);
			Assert.assertNotNull("Could not apply new access");
			Assert.assertFalse(this.tokenService.isExpired(access));
		} catch (OpenstackAPIException e) {
			Assert.fail("Could not apply new access");
		}
		
	}

}
