package com.inforstack.openstack.instance.impl;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.instance.VirtualMachine;
import com.inforstack.openstack.instance.VirtualMachineDao;

@Repository
public class VirtualMachineDaoImpl extends BasicDaoImpl<VirtualMachine> implements VirtualMachineDao {

}
