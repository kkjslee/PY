package com.inforstack.openstack.test.api;

import static org.junit.Assert.fail;

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
import com.inforstack.openstack.api.cinder.Attachment;
import com.inforstack.openstack.api.cinder.CinderService;
import com.inforstack.openstack.api.cinder.Volume;
import com.inforstack.openstack.api.cinder.VolumeType;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.configuration.ConfigurationDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
public class CinderTest {
	
	@Autowired
	private CinderService cinderService;
	
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
	public void testListVolumeTypes() {
		try {
			VolumeType[] types = this.cinderService.listVolumeTypes(this.access);
			Assert.assertNotNull(types);
			Assert.assertTrue(types.length > 0);
			System.out.println("\n\n\n");
			System.out.println("======= Volume Types =======");
			for (VolumeType type : types) {
				System.out.println("---------------------------");
				System.out.println("ID:           " + type.getId());
				System.out.println("Name:         " + type.getName());
				Map<String, String> extra = type.getExtra();
				System.out.println("Extra:        " + (extra != null ? extra.size() : 0));
				if (extra != null) {
					Iterator<Entry<String, String>> it = extra.entrySet().iterator();
					while (it.hasNext()) {
						Entry<String, String> entry = it.next();
						System.out.println(entry.getKey() + ":\t\t" + entry.getValue());
					}
				}
				System.out.println("\n\n");
			}
		} catch (OpenstackAPIException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateAndRemoveVolumeType() {
		try {
			VolumeType[] types = this.cinderService.listVolumeTypes(this.access);
			Assert.assertNotNull(types);
			int size = types.length;
			VolumeType type = this.cinderService.createVolumeType(this.access, "testVolumeType");
			Assert.assertNotNull(type);
			Assert.assertTrue(type.getName().equals("testVolumeType"));
			Assert.assertFalse(type.getId() == null || type.getId().isEmpty());
			Assert.assertTrue((this.cinderService.listVolumeTypes(this.access).length == (size + 1)));
			this.cinderService.removeVolumeType(this.access, type.getId());
			Assert.assertTrue((this.cinderService.listVolumeTypes(this.access).length == size));
		} catch (OpenstackAPIException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testListVolumes() {
		try {
			Volume[] volumes = this.cinderService.listVolumes(this.access);
			Assert.assertNotNull(volumes);
			Assert.assertTrue(volumes.length > 0);
			System.out.println("\n\n\n");
			System.out.println("======= Volumes =======");
			for (Volume volume : volumes) {
				System.out.println("-----------------------");
				System.out.println("ID:           " + volume.getId());
				System.out.println("Name:         " + volume.getName());
				System.out.println("Description:  " + volume.getDescription());
				System.out.println("Size:         " + volume.getSize());
				System.out.println("Bootable:     " + volume.isBootable());
				System.out.println("Type:         " + volume.getType());
				System.out.println("Snapshot:     " + volume.getSnapshot());
				System.out.println("Source:       " + volume.getSource());
				System.out.println("Zone:         " + volume.getZone());
				System.out.println("Created At:   " + volume.getCreated());
				System.out.println("Status:       " + volume.getStatus());
				Map<String, String> metadata = volume.getMetadata();
				System.out.println("Metadata:     " + (metadata != null ? metadata.size() : 0));
				if (metadata != null) {
					Iterator<Entry<String, String>> it = metadata.entrySet().iterator();
					while (it.hasNext()) {
						Entry<String, String> entry = it.next();
						System.out.println(entry.getKey() + ":\t\t" + entry.getValue());
					}
				}
				Attachment[] attachments = volume.getAttachments();
				System.out.println("Attachments:  " + (attachments != null ? attachments.length : 0));
				if (attachments != null) {
					for (Attachment attachment : attachments) {
						System.out.println("{");
						System.out.println("ID:           " + attachment.getId());
						System.out.println("Device:       " + attachment.getDevice());
						System.out.println("Server:       " + attachment.getServer());
						System.out.println("Volume:       " + attachment.getVolume());
						System.out.println("}");
					}
				}
				System.out.println("\n\n");
			}
		} catch (OpenstackAPIException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateAndRemoveVolume() {
		try {
			VolumeType[] types = this.cinderService.listVolumeTypes(this.access);
			Assert.assertNotNull(types);
			Assert.assertTrue(types.length > 0);
			
			Volume[] volumes = this.cinderService.listVolumes(this.access);
			Assert.assertNotNull(volumes);
			int size = volumes.length;
			Volume volume1 = this.cinderService.createVolume(this.access, "testVolume1", "", 1, false, types[0].getId(), "nova");
			Volume volume2 = this.cinderService.createVolumeFromSnapshot(this.access, "testVolume2", "", 1, false, types[0].getId(), "6b0a6566-a6af-4f1b-a42b-c4c833988b77", "nova");
			Volume volume3 = this.cinderService.createVolumeFromVolume(this.access, "testVolume3", "", 1, false, types[0].getId(), volumes[0].getId(), "nova");
			Assert.assertNotNull(volume1);
			Assert.assertTrue(volume1.getName().equals("testVolume1"));
			Assert.assertFalse(volume1.getId() == null || volume1.getId().isEmpty());
			Assert.assertNotNull(volume2);
			Assert.assertTrue(volume2.getName().equals("testVolume2"));
			Assert.assertFalse(volume2.getId() == null || volume2.getId().isEmpty());
			Assert.assertNotNull(volume3);
			Assert.assertTrue(volume3.getName().equals("testVolume3"));
			Assert.assertFalse(volume3.getId() == null || volume3.getId().isEmpty());
			Assert.assertTrue((this.cinderService.listVolumes(this.access).length == (size + 3)));
			volume1 = this.cinderService.getVolume(this.access, volume1.getId());
			while (volume1 != null && !(volume1.getStatus().equalsIgnoreCase("available") || volume1.getStatus().equalsIgnoreCase("error"))) {
				volume1 = this.cinderService.getVolume(this.access, volume1.getId());
			}
			volume2 = this.cinderService.getVolume(this.access, volume2.getId());
			while (volume2 != null && !(volume2.getStatus().equalsIgnoreCase("available") || volume2.getStatus().equalsIgnoreCase("error"))) {
				volume2 = this.cinderService.getVolume(this.access, volume2.getId());
			}
			volume3 = this.cinderService.getVolume(this.access, volume3.getId());
			while (volume3 != null && !(volume3.getStatus().equalsIgnoreCase("available") || volume3.getStatus().equalsIgnoreCase("error"))) {
				volume3 = this.cinderService.getVolume(this.access, volume3.getId());
			}
			this.cinderService.removeVolume(this.access, volume1.getId());
			this.cinderService.removeVolume(this.access, volume2.getId());
			this.cinderService.removeVolume(this.access, volume3.getId());
			volume1 = this.cinderService.getVolume(this.access, volume1.getId());
			while (volume1 != null) {
				volume1 = this.cinderService.getVolume(this.access, volume1.getId());
			}
			volume2 = this.cinderService.getVolume(this.access, volume2.getId());
			while (volume2 != null) {
				volume2 = this.cinderService.getVolume(this.access, volume2.getId());
			}
			volume3 = this.cinderService.getVolume(this.access, volume3.getId());
			while (volume3 != null) {
				volume3 = this.cinderService.getVolume(this.access, volume3.getId());
			}
			Assert.assertTrue((this.cinderService.listVolumes(this.access).length == size));
		} catch (OpenstackAPIException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

}
