package com.inforstack.openstack.promotion;

import java.util.Map;

import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.tenant.Tenant;

public interface PromotionService {
	
	/**
	 * persist promotion
	 * @param promotion
	 * @return
	 */
	public Promotion createPromotion(Promotion promotion);
	
	Promotion createPromotion(String name, Map<Integer, String> displayNames,
			double discount, Integer roleId);
	
	/**
	 * 
	 * @param name
	 * @param displayNames 
	 * @param discount
	 * @param tenant managed tenant instance
	 * @return
	 */
	public Promotion createPromotion(String name, Map<Integer, String> displayNames, double discount, Tenant tenant);
	
	/**
	 * invalid the promotion found by passed id, and create a new one
	 * @param id
	 * @param discount
	 * @param tenant
	 * @return
	 */
	public Promotion editDiscount(Integer id, double discount, Tenant tenant);
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Promotion findPromotionById(Integer id);
	
	/**
	 * Find undeleted promotion
	 * @param name
	 * @param tenant
	 * @return
	 * @throws ApplicationException 
	 */
	public Promotion findPromotionByName(String name, Tenant tenant) throws ApplicationException;
	
	public Promotion removePromotion(Integer id);

}
