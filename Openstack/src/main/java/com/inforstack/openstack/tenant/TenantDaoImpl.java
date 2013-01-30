package com.inforstack.openstack.tenant;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;

@Repository
public class TenantDaoImpl extends BasicDaoImpl<Tenant> implements TenantDao{
	
}
