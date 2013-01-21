package com.inforstack.openstack.promotion;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.i18n.I18n;
import com.inforstack.openstack.i18n.I18nService;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.MapUtil;
import com.inforstack.openstack.utils.StringUtil;

@Service
@Transactional(rollbackFor=Exception.class)
public class PromotionServiceImpl implements PromotionService {
	
	public static final Log log = LogFactory.getLog(PromotionServiceImpl.class);
	@Autowired
	private PromotionDao promotionDao;
	@Autowired
	private I18nService i18nService;
	
	@Override
	public Promotion createPromotion(Promotion promotion) {
		if(promotion == null){
			log.info("Create promotion failed for passed promotion is null");
			return null;
		}
		
		log.debug("Create promotion with name : " + promotion.getName());
		promotionDao.persist(promotion);
		log.debug("Create promotion successfully");
		
		return promotion;
	}

	@Override
	public Promotion createPromotion(String name,
			Map<Integer, String> displayNames, double discount, Tenant tenant) {
		if(StringUtil.isNullOrEmpty(name) || MapUtil.isNullOrEmpty(displayNames)){
			log.info("Create Promotion failed for passed name/displayNames/tenantId is null or empty");
			return null;
		}
		if(discount < 0){
			log.info("Create Promotion failed for passed discount is less than 0");
			return null;
		}
		
		log.debug("Create Promotion with name : " + name);
		List<I18n> i18n = i18nService.createI18n(displayNames,
				Constants.TABLE_PROMOTION,
				Constants.COLUMN_PROMOTION_DISPLAYNAME);
		if(i18n==null || i18n.isEmpty()){
			log.warn("Create Promotion failed for creating i18n of displayname failed");
			return null;
		}
		Promotion promotion = new Promotion();
		promotion.setDiscount(discount);
		promotion.setDisplayName(i18n.get(0).getI18nLink());
		promotion.setName(name);
		promotion.setDeleted(false);
		if(tenant != null){
			promotion.setTenant(tenant);
		}
		promotionDao.persist(promotion);
		
		log.debug("Create promotion successfully");
		return promotion;
	}

	@Override
	public Promotion editDiscount(Integer id, double discount, Tenant tenant) {
		if(id == null){
			log.info("Edit discount failed for passed id is null");
			return null;
		}
		if(discount < 0){
			log.info("Edit discount failed for passed discount is less than 0");
			return null;
		}
		
		log.debug("Edit discount with id : " + id);
		Promotion promotion = promotionDao.findById(id);
		if(promotion == null){
			log.warn("Edit discount failed for no promotion instance found by id : " + id);
			return null;
		}
		if(promotion.getTenant()!=null){
			promotion.setDeleted(true);
		}
		
		Promotion newPromotion = new Promotion();
		newPromotion.setDiscount(discount);
		newPromotion.setDisplayName(promotion.getDisplayName());
		newPromotion.setName(promotion.getName());
		newPromotion.setDeleted(false);
		if(promotion.getTenant()!=null){
			promotion.setTenant(promotion.getTenant());
		}else{
			promotion.setTenant(tenant);
		}
		promotionDao.persist(promotion);
		
		log.debug("Edit discount successfully");
		return newPromotion;
	}

	@Override
	public Promotion findPromotionById(Integer id) {
		if(id == null){
			log.info("Find promotion failed for passed id is null");
			return null;
		}
		
		log.debug("Find promotion by id : " + id);
		Promotion promotion = promotionDao.findById(id);
		if(promotion==null){
			log.info("Find promotion failed for no promotion instance found by id : " + id);
		}else{
			log.info("Find promotion successfully");
		}
		
		return promotion;
	}

	@Override
	public Promotion findPromotionByName(String name, Tenant tenant) throws ApplicationException {
		if(StringUtil.isNullOrEmpty(name)){
			log.info("Find promotion failed for passed name is null or empty");
			return null;
		}
		
		log.debug("Find promotion by name : " + name);
		Promotion promotion = null;
		if(tenant != null){
			promotion = promotionDao.findByNameAndTenant(name, tenant);
		}
		if(promotion == null){
			promotion = promotionDao.findByName(name);
		}
		if(promotion==null){
			log.info("No valid promotion found by name");
		}else{
			log.debug("Find promotion successfully");
		}
		
		return promotion;
	}

	@Override
	public Promotion removePromotion(Integer id) {
		if(id == null){
			log.info("Remove Promotion failed for passed id is null");
			return null;
		}
		
		log.debug("Remove promotion with id : " + id);
		Promotion promotion = promotionDao.findById(id);
		if(promotion==null){
			log.warn("Remove promotion failed for no promotion instance found by id : " + id);
			return null;
		}
		promotion.setDeleted(true);
		log.debug("Remove promotion successfully");
		
		return promotion;
	}
	
	
}
