package com.inforstack.openstack.test.business;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inforstack.openstack.configuration.ConfigurationDao;
import com.inforstack.openstack.utils.HttpClientUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
public class AdministratorTest {

	private String host;
	
	@Autowired
	private ConfigurationDao configurationDao;
	
	@Before
	public void setUp() throws Exception {
		this.host = this.configurationDao.findByName("test.host").getValue();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSignIn() {
		String username = this.configurationDao.findByName("test.admin.username").getValue();
		String password = this.configurationDao.findByName("test.admin.password").getValue();
		String url = this.host + "/Openstack/admin/doLogin";		
		
		try {
			{
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httpost = new HttpPost(url +
		                "?j_username=" + username +
		                "&j_password=" + UUID.randomUUID().toString());
				HttpResponse response = httpclient.execute(httpost);
				if (!HttpClientUtil.checkRedirectLocation(response, this.host, "/Openstack/admin/login;" + HttpClientUtil.getSession(response) + "?error=true")) {
					fail("Can not redirect to relogin page when error.");
				}
			}
			{
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httpost = new HttpPost(url +
		                "?j_username=" + username +
		                "&j_password=" + password);
				HttpResponse response = httpclient.execute(httpost);
				if (!HttpClientUtil.checkRedirectLocation(response, this.host, "/Openstack/admin")) {
					fail("Can not sign in.");
				}
			}
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

}
