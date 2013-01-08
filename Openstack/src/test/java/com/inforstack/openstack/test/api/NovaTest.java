package com.inforstack.openstack.test.api;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
import com.inforstack.openstack.api.nova.flavor.Flavor;
import com.inforstack.openstack.api.nova.flavor.FlavorService;
import com.inforstack.openstack.api.nova.image.Image;
import com.inforstack.openstack.api.nova.image.ImageService;
import com.inforstack.openstack.api.nova.server.Address;
import com.inforstack.openstack.api.nova.server.SecurityGroup;
import com.inforstack.openstack.api.nova.server.Server;
import com.inforstack.openstack.api.nova.server.Server.File;
import com.inforstack.openstack.api.nova.server.ServerService;
import com.inforstack.openstack.configuration.ConfigurationDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
public class NovaTest {

	@Autowired
	private FlavorService flavorService;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private ServerService serverService;
	
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
	public void testListFlavors() {
		try {
			Flavor[] flavors = this.flavorService.listFlavors();
			Assert.assertNotNull(flavors);
			Assert.assertTrue(flavors.length > 0);
			System.out.println("\n\n\n");
			System.out.println("======= Flavors =======");
			for (Flavor flavor : flavors) {
				System.out.println("-----------------------");
				System.out.println("ID:        " + flavor.getId());
				System.out.println("Name:      " + flavor.getName());
				System.out.println("CPU:       " + flavor.getVcpu());
				System.out.println("RAM:       " + flavor.getRam());
				System.out.println("Disk:      " + flavor.getDisk());
				System.out.println("Ephemeral: " + flavor.getEphemeral());
				System.out.println("Factor:    " + flavor.getFactor());
				System.out.println("SWAP:      " + flavor.getSwap());
				System.out.println("Disabled:  " + flavor.isDisabled());
				System.out.println("Public:    " + flavor.isPublic());
			}
		} catch (OpenstackAPIException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testListImages() {
		try {
			Image[] images = this.imageService.listImages();
			Assert.assertNotNull(images);
			Assert.assertTrue(images.length > 0);
			System.out.println("\n\n\n");
			System.out.println("======= Images =======");
			for (Image image : images) {
				System.out.println("----------------------");
				System.out.println("ID:        " + image.getId());
				System.out.println("Name:      " + image.getName());
				System.out.println("Created:   " + image.getCreated());
				System.out.println("Updated:   " + image.getUpdated());
				System.out.println("Tenant:    " + image.getTenant());
				System.out.println("User:      " + image.getUser());
				System.out.println("Status:    " + image.getStatus());
				System.out.println("Progress:  " + image.getProgress());
				System.out.println("MinDisk:   " + image.getMinDisk());
				System.out.println("MinRAM:    " + image.getMinRam());
				System.out.println("Server:    " + (image.getServer() != null ? image.getServer().getId() : null));
				System.out.println("Metadata:  [");
				Map<String, String> metadata = image.getMetadata();
				if (metadata != null) {
					Iterator<Entry<String, String>> it = metadata.entrySet().iterator();
					while (it.hasNext()) {
						Entry<String, String> entry = it.next();
						System.out.println(entry.getKey() + ":\t\t" + entry.getValue());
					}
				}
				System.out.println("]");
			}
		} catch (OpenstackAPIException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testListServers() {
		try {
			if (this.access != null) {
				Server[] servers = this.serverService.listServers(this.access);
				Assert.assertNotNull(servers);
				Assert.assertTrue(servers.length > 0);
				System.out.println("\n\n\n");
				System.out.println("======= Servers =======");
				for (Server server : servers) {
					System.out.println("----------- -----------");
					System.out.println("ID        : " + server.getId());
					System.out.println("Name      : " + server.getName());
					System.out.println("Status    : " + server.getStatus());
					System.out.println("Tenant    : " + server.getTenant());
					System.out.println("User      : " + server.getUser());
					System.out.println("Host ID   : " + server.getHostId());
					System.out.println("Host Name : " + server.getHostName());
					System.out.println("Key Name  : " + server.getKey());
					System.out.println("Image     : " + server.getImage().getId());
					System.out.println("Flavor    : " + server.getFlavor().getId());
					System.out.println("Addresses : {");
					if (server.getAddresses().getPrivateList() != null) {
						System.out.println("        Private : [");
						for (Address address : server.getAddresses().getPrivateList()) {
							System.out.println("                {");
							System.out.println("                        Version  : " + address.getVersion());
							System.out.println("                        Addr     : " + address.getAddr());
							System.out.println("                }");
						}
						System.out.println("        ]");
					}
					if (server.getAddresses().getPublicList() != null) {
						System.out.println("        public : [");
						for (Address address : server.getAddresses().getPublicList()) {
							System.out.println("                {");
							System.out.println("                Version  : " + address.getVersion());
							System.out.println("                Addr     : " + address.getAddr());
							System.out.println("                }");
						}
						System.out.println("        ]");
					}
					System.out.println("}");
					System.out.println("Security  : [");
					for (SecurityGroup securityGroups : server.getSecurityGroups()) {
						System.out.println("        {");
						System.out.println("                Name  : " + securityGroups.getName());
						System.out.println("        }");
					}
					System.out.println("]");
					System.out.println("Updated   : " + server.getUpdated());
					System.out.println("Metadata  : [");
					Map<String, String> metadata = server.getMetadata();
					if (metadata != null) {
						Iterator<Entry<String, String>> it = metadata.entrySet().iterator();
						while (it.hasNext()) {
							Entry<String, String> entry = it.next();
							System.out.println(entry.getKey() + ":\t\t" + entry.getValue());
						}
					}
					System.out.println("]");
				}
			} else {
				fail("Can not get access");
			}
		} catch (OpenstackAPIException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateAndRemoveServer() {
		try {
			if (this.access != null) {
				Server[] servers = this.serverService.listServers(this.access);
				int size = servers.length;
				
				Server newServer = new Server();
				newServer.setName("Test Create Server");
				newServer.setImageRef("2295e9f5-3404-4f77-885b-02af4a78802c");
				newServer.setFlavorRef("1");
				newServer.setMetadata(new HashMap<String, String>());
				newServer.getMetadata().put("1", "M1");
				newServer.getMetadata().put("2", "M2");
				File[] files = new File[1];
				files[0] = new File();
				files[0].setPath("~/test.txt");	
				files[0].setContents("ICAgICAgDQoiQSBjbG91ZCBkb2VzIG5vdCBrbm93IHdoeSBp dCBtb3ZlcyBpbiBqdXN0IHN1Y2ggYSBkaXJlY3Rpb24gYW5k IGF0IHN1Y2ggYSBzcGVlZC4uLkl0IGZlZWxzIGFuIGltcHVs c2lvbi4uLnRoaXMgaXMgdGhlIHBsYWNlIHRvIGdvIG5vdy4g QnV0IHRoZSBza3kga25vd3MgdGhlIHJlYXNvbnMgYW5kIHRo ZSBwYXR0ZXJucyBiZWhpbmQgYWxsIGNsb3VkcywgYW5kIHlv dSB3aWxsIGtub3csIHRvbywgd2hlbiB5b3UgbGlmdCB5b3Vy c2VsZiBoaWdoIGVub3VnaCB0byBzZWUgYmV5b25kIGhvcml6 b25zLiINCg0KLVJpY2hhcmQgQmFjaA==");
				newServer.setPersonality(files);
				
				Server server = this.serverService.createServer(this.access, newServer);
				Assert.assertNotNull(server);
				Assert.assertFalse(server.getId().isEmpty());
				Assert.assertEquals(this.serverService.listServers(this.access).length, size + 1);
				System.out.println("Create Server : " + server.getId());
				
				this.serverService.removeServer(this.access, server);
				
				Assert.assertEquals(this.serverService.listServers(this.access).length, size);
			} else {
				fail("Can not get access");
			}
		} catch (OpenstackAPIException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

}
