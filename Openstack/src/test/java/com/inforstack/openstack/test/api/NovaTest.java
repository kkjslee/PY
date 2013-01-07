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
import com.inforstack.openstack.api.nova.flavor.Flavor;
import com.inforstack.openstack.api.nova.flavor.FlavorService;
import com.inforstack.openstack.api.nova.image.Image;
import com.inforstack.openstack.api.nova.image.ImageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
public class NovaTest {

	@Autowired
	private FlavorService flavorService;
	
	@Autowired
	private ImageService imageService;
	
	@Before
	public void setUp() throws Exception {
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
			}
		} catch (OpenstackAPIException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

}
