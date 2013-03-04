package com.inforstack.openstack.api.cinder.impl;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.RequestBody;
import com.inforstack.openstack.api.cinder.CinderService;
import com.inforstack.openstack.api.cinder.Volume;
import com.inforstack.openstack.api.cinder.VolumeAttachment;
import com.inforstack.openstack.api.cinder.VolumeSnapshot;
import com.inforstack.openstack.api.cinder.VolumeType;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.Access.Service.EndPoint.Type;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.configuration.Configuration;
import com.inforstack.openstack.configuration.ConfigurationDao;
import com.inforstack.openstack.instance.Instance;
import com.inforstack.openstack.instance.InstanceDao;
import com.inforstack.openstack.instance.InstanceStatus;
import com.inforstack.openstack.instance.InstanceStatusDao;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.RestUtils;

@Service("cinderService")
@Transactional
public class CinderServiceImpl implements CinderService {
	
	@Autowired
	private ConfigurationDao configurationDao;
	
	@Autowired
	private KeystoneService keystoneService;
	
	@Autowired
	private InstanceDao instanceDao;
	
	@Autowired
	private InstanceStatusDao instanceStatusDao;
	
	private static VolumeType[] cache = null;
	
	private static Date update = null;
	
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
	public VolumeType[] listVolumeTypes() throws OpenstackAPIException {
		VolumeType[] types = null;
		Date now = new Date();
		if (cache == null || update == null || now.after(update)) {
			Access access = this.keystoneService.getAdminAccess();
			if (access != null) {
				Configuration endpoint = this.configurationDao.findByName(ENDPOINT_VOLUMETYPES);
				if (endpoint != null) {				
					String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
					VolumeTypes response = RestUtils.get(url, access, VolumeTypes.class);
					if (response != null) {
						types = response.getVolumeTypes();
						Configuration expire = this.configurationDao.findByName(CACHE_EXPIRE);
						if (expire != null) {
							cache = types;
							now.setTime(now.getTime() + Integer.parseInt(expire.getValue()) * 60 * 1000);
							update = now;
						}
					}
				}
			}
		} else {
			types = cache;
		}
		
		return types;
	}

	@Override
	public VolumeType getVolumeType(String id) throws OpenstackAPIException {
		VolumeType type = null;
		VolumeType[] types = this.listVolumeTypes();
		if (types != null) {
			for (VolumeType t : types) {
				if (t.getId().equalsIgnoreCase(id)) {
					type = t;
					break;
				}
			}
		}
		return type;
	}

	@Override
	public VolumeType createVolumeType(String name) throws OpenstackAPIException {
		VolumeType type = null;
		Access access = this.keystoneService.getAdminAccess();
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
					cache = null;
				}
			}
		}
		return type;
	}
	
	@Override
	public void updateVolumeType(VolumeType type, String name) throws OpenstackAPIException {
		Access access = this.keystoneService.getAdminAccess();
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
				cache = null;
			}
		}
	}
	
	@Override
	public void removeVolumeType(String id) throws OpenstackAPIException {
		Access access = this.keystoneService.getAdminAccess();
		this.remove(access, ENDPOINT_VOLUMETYPE, id);
		cache = null;
	}
	
	private static final class Volumes {
		
		@JsonProperty("volumes")
		private Volume[] volumes;

		public Volume[] getVolumes() {
			return volumes;
		}
		
	}
	
	private static final class VolumeSnapshots {
		
		@JsonProperty("snapshots")
		private VolumeSnapshot[] volumeSnapshots;

		public VolumeSnapshot[] getVolumeSnapshots() {
			return volumeSnapshots;
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
	
	private static final class VolumeSnapshotBody implements RequestBody {
		
		@JsonProperty("snapshot")
		private VolumeSnapshot snapshot;

		public VolumeSnapshot getSnapshot() {
			return snapshot;
		}

		public void setSnapshot(VolumeSnapshot snapshot) {
			this.snapshot = snapshot;
		}
			
	}

	
	private static final class VolumsesAttach {
			
		@JsonProperty("volumeAttachments")
		private VolumeAttachment[] attachedVolumes;

		public VolumeAttachment[] getVolumes() {
			return attachedVolumes;
		}
		
	}

	private static final class VolumeAttachBody implements RequestBody {
		
		@JsonProperty("volumeAttachment")
		private VolumeAttachment volumeAttach;

		public VolumeAttachment getVolumeAttach() {
			return volumeAttach;
		}

		public void setVolumeAttach(VolumeAttachment volumeAttach) {
			this.volumeAttach = volumeAttach;
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
		return this.getVolumeDetail(access, id);
	}
	
	@Override
	public Volume createVolume(final Access access, String name, String description, int size, boolean bootable, String type, String zone) throws OpenstackAPIException {
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
					
					final String id = volume.getId();
					
					final CinderService self = (CinderService) OpenstackUtil.getBean("cinderService");
					
					Thread thread = new Thread(new Runnable() {

						@Override
						public void run() {
							while (true) {
								try {
									Volume s = CinderServiceImpl.this.getVolumeDetail(access, id);
									if (s != null) {
										String status = s.getStatus();
										self.updateVolumeStatus(s.getId(), status);
										if (status.equalsIgnoreCase("available") || status.equalsIgnoreCase("error")) {
											break;
										}
									} else {
										break;
									}
									Thread.sleep(500);
								} catch (OpenstackAPIException e) {
									break;
								} catch (InterruptedException e) {
									break;
								}
							}
						}
						
					}, "Creating Volume " + volume.getId());
					thread.start();
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
	public void removeVolume(final Access access, final String id) throws OpenstackAPIException {
		this.remove(access, ENDPOINT_VOLUME, id);
		
		final CinderService self = (CinderService) OpenstackUtil.getBean("cinderService");
		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Volume s = CinderServiceImpl.this.getVolumeDetail(access, id);
						if (s != null) {
							String status = s.getStatus();
							self.updateVolumeStatus(s.getId(), status);
							if (status.equalsIgnoreCase("error")) {
								break;
							}
						} else {
							self.updateVolumeStatus(id, "deleted");
							break;
						}
						Thread.sleep(500);
					} catch (OpenstackAPIException e) {
						break;
					} catch (InterruptedException e) {
						break;
					}
				}
			}
			
		}, "Creating Volume " + id);
		thread.start();
	}
	
	@Override
	public void updateVolumeStatus(String uuid, String status) {
		Instance instance = this.instanceDao.findByObject("uuid", uuid);
		if (instance != null) {
			Date now = new Date();
			String oldStatus = instance.getStatus();
			if (!oldStatus.equals(status)) {
				InstanceStatus instanceStatus = new InstanceStatus();
				instanceStatus.setUuid(uuid);
				instanceStatus.setStatus(status);
				instanceStatus.setUpdateTime(now);				
				this.instanceStatusDao.persist(instanceStatus);
			}
			instance.setStatus(status);
			instance.setTask(null);
			instance.setUpdateTime(now);
		}
	}
	
	@Override
	public VolumeAttachment attachVolume(final Access access, String serverId, final String volumeId, String device) throws OpenstackAPIException {
		VolumeAttachment va = null;
		if (access != null && serverId != null && !volumeId.trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_VOLUMEATTACH);
			if (endpoint != null) {
				String url = getnovaEndpoint(access, Type.INTERNAL, endpoint.getValue());
				try {
					va = new VolumeAttachment();
					va.setDevice(device);
					va.setVolumeId(volumeId);
					VolumeAttachBody request = new VolumeAttachBody();
					request.setVolumeAttach(va);
					
					VolumeAttachBody response = RestUtils.postForObject(url, access, request, VolumeAttachBody.class, serverId);
					if (response != null) {
						va = response.getVolumeAttach();
						
						final CinderService self = (CinderService) OpenstackUtil.getBean("cinderService");
						
						Thread thread = new Thread(new Runnable() {

							@Override
							public void run() {
								while (true) {
									try {
										Volume s = CinderServiceImpl.this.getVolumeDetail(access, volumeId);
										if (s != null) {
											String status = s.getStatus();
											self.updateVolumeStatus(s.getId(), status);
											if (status.equalsIgnoreCase("error") || status.equalsIgnoreCase("in-use") || status.equalsIgnoreCase("available")) {
												break;
											}
										}
										Thread.sleep(500);
									} catch (OpenstackAPIException e) {
										break;
									} catch (InterruptedException e) {
										break;
									}
								}
							}
							
						}, "Attaching Volume " + volumeId);
						thread.start();
					}
				} catch (OpenstackAPIException e) {
					RestUtils.handleError(e);
				}
			}
		}
		return va;		
	}

	@Override
	public void detachVolume(Access access, String serverId, String attachId) throws OpenstackAPIException {
		if (access != null && serverId != null && !attachId.trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_VOLUMEATTACH_DETAIL);
			if (endpoint != null) {
				String url = getnovaEndpoint(access, Type.INTERNAL, endpoint.getValue());
				try {
					RestUtils.delete(url, access, serverId,attachId);
				} catch (OpenstackAPIException e) {
					RestUtils.handleError(e);
				}
			}
		}
		
	}

	@Override
	public VolumeAttachment[] listAttachedVolumes(Access access, String serverId) throws OpenstackAPIException {
		VolumeAttachment[] volumes = null;
		if (access != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_VOLUMEATTACH);
			if (endpoint != null) {
				String url = getnovaEndpoint(access, Type.INTERNAL, endpoint.getValue());
				VolumsesAttach response = RestUtils.get(url, access, VolumsesAttach.class, serverId);
				if (response != null) {
					volumes = response.getVolumes();
				}
			}
		}
		return volumes;
	}
	
	private static String getEndpoint(Access access, Type type, String suffix) {
		return RestUtils.getEndpoint(access, "cinder", type, suffix);
	}
	
	private static String getnovaEndpoint(Access access, Type type, String suffix) {
		return RestUtils.getEndpoint(access, "nova", type, suffix);
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
	
	private Volume getVolumeDetail(Access access, String id) throws OpenstackAPIException {
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
	public VolumeSnapshot createVolumeSnapshot(Access access, String volumeID, String name,
			String description, boolean force) throws OpenstackAPIException {
		
		VolumeSnapshot volumeSnapshot = null;
		if (access != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SNAPSHOTS);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				
				volumeSnapshot = new VolumeSnapshot();
				volumeSnapshot.setVolume_id(volumeID);
				volumeSnapshot.setDisplay_name(name);
				volumeSnapshot.setDisplay_description(description);
				volumeSnapshot.setForce(force);
				
				VolumeSnapshotBody request = new VolumeSnapshotBody();
				request.setSnapshot(volumeSnapshot);
				VolumeSnapshotBody response = RestUtils.postForObject(url, access, request, VolumeSnapshotBody.class);
				if (response != null) {
					volumeSnapshot = response.getSnapshot();
				}
			}			
		}
		return volumeSnapshot;
	}

	@Override
	public VolumeSnapshot[] listVolumeSnapshots(Access access)
			throws OpenstackAPIException {
		VolumeSnapshot[] volumeSnapshots = null;
		if (access != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SNAPSHOTS);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				VolumeSnapshots response = RestUtils.get(url, access, VolumeSnapshots.class);
				if (response != null) {
					volumeSnapshots = response.getVolumeSnapshots();
				}
			}
		}
		return volumeSnapshots;
	}

	@Override
	public VolumeSnapshot getDetailVolumeSnapshot(Access access,
			String volumeSnapshotID) throws OpenstackAPIException {
		VolumeSnapshot volumeSnapshot = null;
		if (access != null && volumeSnapshotID != null && !volumeSnapshotID.trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SNAPSHOT);
			if (endpoint != null) {
				String url = getEndpoint(access, Type.INTERNAL, endpoint.getValue());
				try {
					VolumeSnapshotBody response = RestUtils.get(url, access, VolumeSnapshotBody.class, volumeSnapshotID);
					if (response != null) {
						volumeSnapshot = response.getSnapshot();
					}
				} catch (OpenstackAPIException e) {
					RestUtils.handleError(e);
				}
			}
		}
		return volumeSnapshot;
	}

	@Override
	public void deletelVolumeSnapshot(Access access,
			String volumeSnapshotID) throws OpenstackAPIException {
		if (access != null && volumeSnapshotID != null && !volumeSnapshotID.trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_SNAPSHOT);
			if (endpoint != null) {
				String url = getnovaEndpoint(access, Type.INTERNAL, endpoint.getValue());
				try {
					RestUtils.delete(url, access, volumeSnapshotID);
				} catch (OpenstackAPIException e) {
					RestUtils.handleError(e);
				}
			}
		}
	}

}
