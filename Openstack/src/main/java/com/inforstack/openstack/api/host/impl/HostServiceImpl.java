package com.inforstack.openstack.api.host.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.host.Host;
import com.inforstack.openstack.api.host.HostDescribes;
import com.inforstack.openstack.api.host.HostService;
import com.inforstack.openstack.api.host.Hosts;
import com.inforstack.openstack.api.token.Access;
import com.inforstack.openstack.api.token.KeystoneService;
import com.inforstack.openstack.configuration.Configuration;
import com.inforstack.openstack.configuration.ConfigurationDao;
import com.inforstack.openstack.utils.RestUtils;

@Service
@Transactional
public class HostServiceImpl implements HostService {

	@Autowired
	private ConfigurationDao configurationDao;
	
	@Autowired
	private KeystoneService tokenService;
	
	@Override
	public Host[] getHosts() throws OpenstackAPIException {
		Host[] hosts = null;
		
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_HOSTS);
		if (endpoint != null) {
			Access access = this.tokenService.getAdminAccess();
			Hosts response = RestUtils.get(endpoint.getValue(), access, Hosts.class, access.getToken().getTenant().getId());
			if (response != null) {
				hosts = response.getHosts();
			}
		}
		return hosts;
	}

	@Override
	public HostDescribes getHostDescribe(Host host) throws OpenstackAPIException {
		HostDescribes describe = null;
		if (host != null) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_HOST_DESCRIBE);
			if (endpoint != null) {
				Access access = this.tokenService.getAdminAccess();
				describe = RestUtils.get(endpoint.getValue(), access, HostDescribes.class, access.getToken().getTenant().getId(), host.getHost_name());
			}
		}
		return describe;
	}

}
