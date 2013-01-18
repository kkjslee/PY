package com.inforstack.openstack.api.nova.image.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.keystone.Access.Service.EndPoint.Type;
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
	
	private static Image[] cache = null;
	
	private static Date update = null;
	
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
		Date now = new Date();
		if (cache == null || update == null || now.after(update)) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_IMAGES_DETAIL);
			if (endpoint != null) {
				Access access = this.tokenService.getAdminAccess();
				String url = getEndpoint(access, Type.ADMIN, endpoint.getValue());
				Images response = RestUtils.get(url, access, Images.class);
				if (response != null) {
					images = response.getImages();
					Configuration expire = this.configurationDao.findByName(CACHE_EXPIRE);
					if (expire != null) {
						cache = images;
						now.setTime(now.getTime() + Integer.parseInt(expire.getValue()) * 60 * 1000);
						update = now;
					}
				}
			}
		} else {
			images = cache;
		}
		return images;
	}

	@Override
	public Image getImage(String id) throws OpenstackAPIException {
		Image image = null;
		Image[] images = this.listImages();
		if (images != null) {
			for (Image f : images) {
				if (f.getId().equalsIgnoreCase(id)) {
					image = f;
					break;
				}
			}
		}
		return image;
	}
	
	private static String getEndpoint(Access access, Type type, String suffix) {
		return RestUtils.getEndpoint(access, "nova", type, suffix);
	}

}
