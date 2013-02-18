package com.inforstack.openstack.api.cinder;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;

public interface CinderService {

	public final static String ENDPOINT_VOLUMES				= "openstack.endpoint.volumes";
	public final static String ENDPOINT_VOLUMES_DETAIL		= "openstack.endpoint.volumes.detail";
	public final static String ENDPOINT_VOLUME				= "openstack.endpoint.volume";
	
	public final static String ENDPOINT_VOLUMETYPES			= "openstack.endpoint.volumetypes";
	public final static String ENDPOINT_VOLUMETYPE			= "openstack.endpoint.volumetype";
	
	public final static String ENDPOINT_SNAPSHOTS			= "openstack.endpoint.volumesnapshots";
	public final static String ENDPOINT_SNAPSHOTS_DETAIL	= "openstack.endpoint.volumesnapshots.detail";
	public final static String ENDPOINT_SNAPSHOT			= "openstack.endpoint.volumesnapshot";
	
	public VolumeType[] listVolumeTypes(Access access) throws OpenstackAPIException;
	
	public VolumeType getVolumeType(Access access, String id) throws OpenstackAPIException;
	
	public VolumeType createVolumeType(Access access, String name) throws OpenstackAPIException;
	
	public void updateVolumeType(Access access, VolumeType type, String name) throws OpenstackAPIException;
	
	public void removeVolumeType(Access access, String id) throws OpenstackAPIException;
	
	public Volume[] listVolumes(Access access) throws OpenstackAPIException;
	
	public Volume getVolume(Access access, String id) throws OpenstackAPIException;
	
	public Volume createVolume(Access access, String name, String description, int size, boolean bootable, String type, String zone) throws OpenstackAPIException;
	
	public Volume createVolumeFromSnapshot(Access access, String name, String description, int size, boolean bootable, String type, String snapshot, String zone) throws OpenstackAPIException;
	
	public Volume createVolumeFromVolume(Access access, String name, String description, int size, boolean bootable, String type, String source, String zone) throws OpenstackAPIException;
	
	public void removeVolume(Access access, String id) throws OpenstackAPIException;
	
}
