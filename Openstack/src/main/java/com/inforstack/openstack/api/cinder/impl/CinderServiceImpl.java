package com.inforstack.openstack.api.cinder.impl;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.RequestBody;
import com.inforstack.openstack.api.cinder.CinderService;
import com.inforstack.openstack.api.cinder.Volume;
import com.inforstack.openstack.api.cinder.VolumeType;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.Access.Service.EndPoint.Type;
import com.inforstack.openstack.configuration.Configuration;
import com.inforstack.openstack.configuration.ConfigurationDao;
import com.inforstack.openstack.utils.RestUtils;

@Service
@Transactional
public class CinderServiceImpl implements CinderService {
	
	@Autowired
	private ConfigurationDao configurationDao;
	
	private static final class VolumeTypes {
		
		@JsonProperty("volume_types")
		private VolumeType[] volumeTypes;

		public VolumeType[] getVolumeTypes() {
			return volumeTypes;
		}
		
	}
	
	private static final class VolumeTypeBody implements RequestBody {
		
		@JsonProperty("volume_type")
		private VolumeType volumeType;
		
		public VolumeType getVolumeType() {
			return this.volumeType;
		}
		
		public void setVolumeType(VolumeType network) {
			this.volumeType = network;
		}
		
	}

	@Override
	public VolumeType[] listVolumeTypes(Access access) throws OpenstackAPIException {
		VolumeType[] types = null;
		if (access != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_VOLUMETYPES);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				VolumeTypes response = RestUtils.get(url, access, VolumeTypes.class);
				if (response != null) {
					types = response.getVolumeTypes();
				}
			}
		}
		return types;
	}

	@Override
	public VolumeType getVolumeType(Access access, String id) throws OpenstackAPIException {
		VolumeType type = null;
		if (access != null && id != null && !id.trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_VOLUMETYPE);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				try {
					VolumeTypeBody response = RestUtils.get(url, access, VolumeTypeBody.class, id);
					if (response != null) {
						type = response.getVolumeType();
					}
				} catch (OpenstackAPIException e) {
					RestUtils.handleError(e);
				}
			}
		}
		return type;
	}

	@Override
	public VolumeType createVolumeType(Access access, String name) throws OpenstackAPIException {
		VolumeType type = null;
		if (access != null && !name.trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_VOLUMETYPES);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				VolumeType typeInstance = new VolumeType();
				typeInstance.setName(name);
				VolumeTypeBody request = new VolumeTypeBody();
				request.setVolumeType(typeInstance);
				VolumeTypeBody response = RestUtils.postForObject(url, access, request, VolumeTypeBody.class);
				if (response != null) {
					type = response.getVolumeType();
				}
			}
		}
		return type;
	}
	
	@Override
	public void updateVolumeType(Access access, VolumeType type, String name) throws OpenstackAPIException {
		if (access != null && !type.getId().trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_VOLUMETYPE);
			if (endpoint != null) {
				VolumeType instance = new VolumeType();
				instance.setId(type.getId());
				instance.setName(name);
				instance.setExtra(type.getExtra());
				
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				VolumeTypeBody request = new VolumeTypeBody();
				request.setVolumeType(instance);
				RestUtils.put(url, access, request, type.getId());
				
				type.setName(name);
			}
		}
	}
	
	@Override
	public void removeVolumeType(Access access, String id) throws OpenstackAPIException {
		this.remove(access, ENDPOINT_VOLUMETYPE, id);
	}
	
	private static final class Volumes {
		
		@JsonProperty("volumes")
		private Volume[] volumes;

		public Volume[] getVolumes() {
			return volumes;
		}
		
	}
	
	private static final class VolumeBody implements RequestBody {
		
		@JsonProperty("volume")
		private Volume volume;
		
		public Volume getVolume() {
			return this.volume;
		}
		
		public void setVolume(Volume volume) {
			this.volume = volume;
		}
		
	}

	@Override
	public Volume[] listVolumes(Access access) throws OpenstackAPIException {
		Volume[] volumes = null;
		if (access != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_VOLUMES);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				Volumes response = RestUtils.get(url, access, Volumes.class);
				if (response != null) {
					volumes = response.getVolumes();
				}
			}
		}
		return volumes;
	}
	
	@Override
	public Volume getVolume(Access access, String id) throws OpenstackAPIException {
		Volume volume = null;
		if (access != null && id != null && !id.trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_VOLUME);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				try {
					VolumeBody response = RestUtils.get(url, access, VolumeBody.class, id);
					if (response != null) {
						volume = response.getVolume();
					}
				} catch (OpenstackAPIException e) {
					RestUtils.handleError(e);
				}
			}
		}
		return volume;
	}
	
	@Override
	public Volume createVolume(Access access, String name, String description, int size, boolean bootable, String type, String zone) throws OpenstackAPIException {
		Volume volume = null;
		if (access != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_VOLUMES);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				
				Volume instance = new Volume();
				instance.setName(name);
				instance.setDescription(description);
				instance.setSize(size);
				instance.setBootable(bootable);
				instance.setType(type);
				instance.setZone(zone);
				
				VolumeBody request = new VolumeBody();
				request.setVolume(instance);
				VolumeBody response = RestUtils.postForObject(url, access, request, VolumeBody.class);
				if (response != null) {
					volume = response.getVolume();
				}
			}			
		}
		return volume;
	}

	@Override
	public Volume createVolumeFromSnapshot(Access access, String name, String description, int size, boolean bootable, String type, String snapshot, String zone) throws OpenstackAPIException {
		Volume volume = null;
		if (access != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_VOLUMES);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				
				Volume instance = new Volume();
				instance.setName(name);
				instance.setDescription(description);
				instance.setSize(size);
				instance.setBootable(bootable);
				instance.setType(type);
				instance.setSnapshot(snapshot);
				instance.setZone(zone);
				
				VolumeBody request = new VolumeBody();
				request.setVolume(instance);
				VolumeBody response = RestUtils.postForObject(url, access, request, VolumeBody.class);
				if (response != null) {
					volume = response.getVolume();
				}
			}			
		}
		return volume;
	}
	
	@Override
	public Volume createVolumeFromVolume(Access access, String name, String description, int size, boolean bootable, String type, String source, String zone) throws OpenstackAPIException {
		Volume volume = null;
		if (access != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_VOLUMES);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				
				Volume instance = new Volume();
				instance.setName(name);
				instance.setDescription(description);
				instance.setSize(size);
				instance.setBootable(bootable);
				instance.setType(type);
				instance.setSource(source);
				instance.setZone(zone);
				
				VolumeBody request = new VolumeBody();
				request.setVolume(instance);
				VolumeBody response = RestUtils.postForObject(url, access, request, VolumeBody.class);
				if (response != null) {
					volume = response.getVolume();
				}
			}			
		}
		return volume;
	}
	
	@Override
	public void removeVolume(Access access, String id) throws OpenstackAPIException {
		this.remove(access, ENDPOINT_VOLUME, id);		
	}
	
	private static String getEndpoint(Access access, Type type, String suffix) {
		return RestUtils.getEndpoint(access, "cinder", type, suffix);
	}
	
	private void remove(Access access, String urlName, String id) throws OpenstackAPIException {
		if (access != null && !id.trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(urlName);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				RestUtils.delete(url, access, id);
			}
		}
	}

}
