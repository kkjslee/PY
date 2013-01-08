package com.inforstack.openstack.tenant;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.utils.Constants;

@Service
@Transactional
public class TenantServiceImpl implements TenantService {
	
	private static final Log log = LogFactory.getLog(TenantServiceImpl.class);
	private TenantDao tenantDao;
	
	@Override
	public Tenant createTenant(Tenant tenant) {
		if(tenant == null){
			log.debug("Create tenant failed for null is passed");
		}
		
		log.debug("create tenant : " + tenant.getName());
		tenant.setAgeing(Constants.TENANT_AGEING_ACTIVE);
		tenant.setCreateTime(new Date());
		Tenant t = tenantDao.persist(tenant);
		if(t == null){
			log.debug("create failed ");
			return null;
		}else{
			log.debug("create successfully ");
			return t;
		}
	}
	
}
