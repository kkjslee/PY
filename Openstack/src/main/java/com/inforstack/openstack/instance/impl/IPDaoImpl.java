package com.inforstack.openstack.instance.impl;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.instance.IP;
import com.inforstack.openstack.instance.IPDao;

@Repository
public class IPDaoImpl extends BasicDaoImpl<IP> implements IPDao {

}
