package com.inforstack.openstack.item;

import com.inforstack.openstack.basic.BasicDao;

public interface VolumeTypeDao extends BasicDao<VolumeType> {

	public String getVolumeRefId(int dataCenterId, String uuid);
	
}
