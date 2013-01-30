package com.inforstack.openstack.security.role;

import javax.persistence.EntityManager;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;


@Repository
public class RoleDaoImpl extends BasicDaoImpl<Role> implements RoleDao {
	
}
