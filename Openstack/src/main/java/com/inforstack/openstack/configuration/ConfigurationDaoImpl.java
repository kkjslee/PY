package com.inforstack.openstack.configuration;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;

@Repository
public class ConfigurationDaoImpl extends BasicDaoImpl<Configuration> implements ConfigurationDao {

	@Override
	public Configuration findByName(String name) {
		return this.findByObject("name", name);
	}

}
