package com.inforstack.openstack.configuration;

public interface ConfigurationDao {

	public Configuration findByName(String name);
	
}
