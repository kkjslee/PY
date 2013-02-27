package com.inforstack.openstack.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.log.Logger;

@Service
@Transactional
public class ConfigurationServiceImpl implements ConfigurationService{
	
	private static final Logger log = new Logger(ConfigurationServiceImpl.class);
	@Autowired
	private ConfigurationDao configurationDao;
	
	@Override
	public String getValueByName(String name){
		log.debug("Find configuration by name : " + name);
		Configuration conf = configurationDao.findByName(name);
		if(conf != null){
			log.debug("Find successfully");
			return conf.getValue();
		}
		
		log.debug("Find failed");
		return null;
	}
}
