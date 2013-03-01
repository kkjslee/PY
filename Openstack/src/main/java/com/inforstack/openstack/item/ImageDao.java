package com.inforstack.openstack.item;

import com.inforstack.openstack.basic.BasicDao;

public interface ImageDao extends BasicDao<Image> {

	public String getImageRefId(int dataCenterId, String uuid);
	
}
