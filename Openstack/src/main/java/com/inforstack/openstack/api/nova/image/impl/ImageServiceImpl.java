package com.inforstack.openstack.api.nova.image.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.nova.image.Image;
import com.inforstack.openstack.api.nova.image.ImageService;
import com.inforstack.openstack.configuration.Configuration;
import com.inforstack.openstack.configuration.ConfigurationDao;
import com.inforstack.openstack.utils.RestUtils;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ConfigurationDao configurationDao;
	
	@Autowired
	private KeystoneService tokenService;
	
	public static final class Images {
		
		private Image[] images;

		public Image[] getImages() {
			return images;
		}

		public void setImages(Image[] images) {
			this.images = images;
		}
		
	}
	
	@Override
	public Image[] listImages() throws OpenstackAPIException {
		Image[] images = null;
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_IMAGES);
		if (endpoint != null) {
			Access access = this.tokenService.getAdminAccess();
			Images response = RestUtils.get(endpoint.getValue(), access, Images.class, access.getToken().getTenant().getId());
			if (response != null) {
				images = response.getImages();
			}
		}
		return images;
	}

}
