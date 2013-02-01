package com.inforstack.openstack.instance.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.nova.server.Server;
import com.inforstack.openstack.api.nova.server.ServerService;
import com.inforstack.openstack.instance.InstanceService;
import com.inforstack.openstack.instance.InstanceStatusDao;
import com.inforstack.openstack.instance.UserActionDao;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.user.User;

@Service
@Transactional
public class InstanceServiceImpl implements InstanceService {
	
	@Autowired
	private UserActionDao userActionDao;
	
	@Autowired
	private InstanceStatusDao instanceStatusDao;
	
	@Autowired
	private KeystoneService keystoneService;
	
	@Autowired
	private ServerService serverService;

	@Override
	public void createVM(User user, Tenant tenant, Server server) {
		try {
			Access access = this.keystoneService.getAccess(user.getName(), user.getPassword(), tenant.getUuid(), true);
			if (access != null) {
				Server newServer = this.serverService.createServer(access, server);
			}
		} catch (OpenstackAPIException e) {
		}
	}

	@Override
	public void updateVM(User user, Tenant tenant, Server server) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeVM(User user, Tenant tenant, Server server) {
		// TODO Auto-generated method stub
		
	}

}
