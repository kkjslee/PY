package com.inforstack.openstack.tenant;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.utils.Constants;

@Service("tenantService")
@Transactional(rollbackFor=Exception.class)
public class TenantServiceImpl implements TenantService {
	
	private static final Log log = LogFactory.getLog(TenantServiceImpl.class);
	@Autowired
	private TenantDao tenantDao;
	@Autowired
	private KeystoneService keystoneService;
	
	@Override
	public Tenant createTenant(Tenant tenant) throws OpenstackAPIException  {
		if(tenant == null){
			log.info("Create tenant failed for null is passed");
			return null;
		}
		
		log.debug("create tenant : " + tenant.getName());
		tenant.setAgeing(Constants.TENANT_AGEING_ACTIVE);
		tenant.setCreateTime(new Date());
		tenantDao.persist(tenant);
		
		tenant.setOpenstatckTenant(keystoneService.createTenant(tenant.getName(), "", true));
		tenant.setUuid(tenant.getOpenstatckTenant().getId());
		log.debug("create successfully ");
		return tenant;
	}

	@Override
	public Tenant findTenantById(Integer tenantId) {
		if(tenantId==null){
			log.info("Find tenant by id failed for passed id is null");
			return null;
		}
		
		log.debug("find tenant by id :" + tenantId);
		Tenant t = tenantDao.findById(tenantId);
		if(t == null){
			log.info("No tenant found for tenant id : " + tenantId);
		}else{
			log.debug("Tenant found successfully by id : " + tenantId);
		}
		
		return t;
	}
	
}
