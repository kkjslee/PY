package com.inforstack.openstack.test.api;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.inforstack.openstack.api.token.Token;
import com.inforstack.openstack.utils.TokenUtils;

public class TokenTest {
	
	private String tenant = "b7c22b6d6610409e9f7d18ad5761112f";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetToken() {
		Token token = TokenUtils.getToken("demo", "inforstack", tenant, false);
		if (token == null) {
			token = TokenUtils.getToken("demo", "inforstack", tenant, true);
		}
		Assert.assertNotNull("Could not get token", token);
	}

	@Test
	public void testApplyToken() {
		Token token = TokenUtils.applyToken("admin", "inforstack", tenant);
		Assert.assertNotNull("Could not apply new token", token);
	}

}
