package com.inforstack.openstack.instance.impl;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.instance.AttachTask;
import com.inforstack.openstack.instance.AttachTaskDao;

@Repository
public class AttachTaskDaoImpl extends BasicDaoImpl<AttachTask> implements AttachTaskDao {

}
