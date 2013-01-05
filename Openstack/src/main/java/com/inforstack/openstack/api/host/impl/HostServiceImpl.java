package com.inforstack.openstack.api.host.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.host.Host;
import com.inforstack.openstack.api.host.HostResponse;
import com.inforstack.openstack.api.host.HostService;
import com.inforstack.openstack.api.token.Access;
import com.inforstack.openstack.api.token.TokenService;
import com.inforstack.openstack.configuration.Configuration;
import com.inforstack.openstack.configuration.ConfigurationDao;
import com.inforstack.openstack.utils.RestUtils;

@Service
@Transactional
public class HostServiceImpl implements HostService {

	@Autowired
	private ConfigurationDao configurationDao;
	
	@Autowired
	private TokenService tokenService;
	
	@Override
	public Host[] getHosts() throws OpenstackAPIException {
		Host[] hosts = null;
		
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_HOST);
		if (endpoint != null) {
			Access access = this.tokenService.getAdminAccess();
			HostResponse response = RestUtils.get(endpoint.getValue(), access, HostResponse.class, access.getToken().getTenant().getId());
			if (response != null) {
				hosts = response.getHosts();
			}
		}
		return hosts;
	}

}
