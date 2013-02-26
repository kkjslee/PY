package com.inforstack.openstack.instance.impl;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.instance.VolumeInstance;
import com.inforstack.openstack.instance.VolumeInstanceDao;

@Repository
public class VolumeInstanceDaoImpl extends BasicDaoImpl<VolumeInstance> implements VolumeInstanceDao {

}
