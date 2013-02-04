package com.inforstack.openstack.tenant;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.promotion.Promotion;
import com.inforstack.openstack.promotion.PromotionService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;

@Service("tenantService")
@Transactional(rollbackFor=Exception.class)
public class TenantServiceImpl implements TenantService {
	
	private static final Logger log = new Logger(TenantServiceImpl.class);
	@Autowired
	private TenantDao tenantDao;
	@Autowired
	private KeystoneService keystoneService;
	@Autowired
	private PromotionService promotionService;
	
	@Override
	public Tenant createTenant(Tenant tenant, int roleId) throws OpenstackAPIException  {
		log.debug("create tenant : " + tenant.getName());
		tenant.setRoleId(roleId);
		tenant.setAgeing(Constants.TENANT_AGEING_ACTIVE);
		tenant.setCreateTime(new Date());
		tenantDao.persist(tenant);
		
		tenant.setOpenstatckTenant(keystoneService.createTenant(tenant.getName(), "", true));
		tenant.setUuid(tenant.getOpenstatckTenant().getId());
		log.debug("create successfully ");
		return tenant;
	}

	@Override
	public Tenant findTenantById(int tenantId) {
		log.debug("find tenant by id :" + tenantId);
		Tenant t = tenantDao.findById(tenantId);
		if(t == null){
			log.info("No tenant found for tenant id : " + tenantId);
		}else{
			log.debug("Tenant found successfully by id : " + tenantId);
		}
		
		return t;
	}
	
	@Override
	public Tenant findTenantWithPromotion(int tenantId){
		log.debug("Fidn tenant with promotion by tenant id : " + tenantId);
		TenantService self = (TenantService)OpenstackUtil.getBean("tenantService");
		Tenant tenant = self.findTenantById(tenantId);
		if(tenant == null){
			log.info("Find tenant with promotion failed for no tenant found by id : " + tenantId);
			return null;
		}
		tenant.getPromotion().getId();
		log.debug("Find tenant with promotion successfully");
		
		return tenant;
	}
	
	@Override
	public Tenant findAgent(int agentId){
		log.debug("find agent by id :" + agentId);
		Tenant t = tenantDao.findById(agentId);
		if(t != null && t.getRoleId() != Constants.ROLE_AGENT){
			log.info("Tenant found with role : " + t.getRoleId());
			t = null;
		}
		
		if(t == null){
			log.info("No tenant found for tenant id : " + agentId);
		}else{
			log.debug("Tenant found successfully by id : " + agentId);
		}
		
		return t;
	}

	@Override
	public Tenant updateTenant(Tenant tenant) {
		log.debug("Update tenant with name : " + tenant.getName());
		tenantDao.update(tenant);
		log.debug("Update tenant successfully");
		return tenant;
	}

	@Override
	public Tenant removeTenant(Tenant tenant) {
		if(tenant == null){
			log.info("Remove tenant failed for passed tenant is null");
			return null;
		}
		
		log.debug("Remove tenant with name : " + tenant.getName());
		tenantDao.remove(tenant);
		log.debug("Remove tenant successfully");
		return tenant;
	}

	@Override
	public Tenant removeTenant(int tenantId) {
		log.debug("Remove tenant with id : " + tenantId);
		Tenant tenant =  tenantDao.findById(tenantId);
		if(tenant==null){
			log.info("Remove tenant failed for no tenant instance found by id : " + tenantId);
			return null;
		}
		tenantDao.remove(tenant);
		log.debug("Remove tenant successfully");
		return tenant;
	}

	@Override
	public Tenant setPromotion(int tenantId, double discount) {
		if(discount < 0){
			log.info("Set promotion failed for passed discount is less than 0");
			return null;
		}
		
		log.debug("Set the promotion of tenant['"+tenantId+"'] to " + discount);
		TenantService self = (TenantService)OpenstackUtil.getBean("tenantService");
		Tenant tenant = self.findTenantById(tenantId);
		if(tenant == null){
			log.info("Set promotion failed for no tenant instance found by id : " + tenantId);
			return null;
		}
		Promotion promotion = tenant.getPromotion();
		Promotion newPromotion = promotionService.editDiscount(promotion.getId(), discount, tenant);
		if(newPromotion==null){
			log.warn("Set prommtion failed for edit discount failed");
			return null;
		}
		tenant.setPromotion(newPromotion);
		log.debug("Set promotion successfully");
		return tenant;
	}
	
}
