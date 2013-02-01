package com.inforstack.openstack.configuration;

import com.inforstack.openstack.basic.BasicDao;

public interface ConfigurationDao extends BasicDao<Configuration> {

	public Configuration findByName(String name);
	
}
