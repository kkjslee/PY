package com.inforstack.openstack.security.group;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SecurityGroupServiceImpl implements SecurityGroupService{
	
	private static final Log log = LogFactory.getLog(SecurityGroupServiceImpl.class);
	
	@Autowired
	private SecurityGroupDao securityGroupDao;
}
