package com.inforstack.openstack.promotion;

import java.util.List;

import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.tenant.Tenant;

public interface PromotionDao {

	public void persist(Promotion promotion);

	public Promotion findById(Integer id);

	public Promotion findByName(String name) throws ApplicationException;
	
	public Promotion findByNameAndTenant(String name, Tenant tenant) throws ApplicationException;
	
	public List<Promotion> findAllByName(String name);
	
	public boolean exist(String name) throws ApplicationException;

}
