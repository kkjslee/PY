package com.inforstack.openstack.instance.impl;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.instance.InstanceStatus;
import com.inforstack.openstack.instance.InstanceStatusDao;

@Repository
public class InstanceStatusDaoImpl extends BasicDaoImpl<InstanceStatus> implements InstanceStatusDao {

}
