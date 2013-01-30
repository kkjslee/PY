package com.inforstack.openstack.promotion;

import java.util.List;

import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.utils.db.IDao;

public interface PromotionDao extends IDao<Promotion> {

	public Promotion findByNameAndTenant(String name, Tenant tenant) throws ApplicationException;
	
	public Promotion findByNameAndRole(String name, int roleId) throws ApplicationException;
	
	public List<Promotion> findAllByName(String name);
	
	public boolean exist(String name) throws ApplicationException;


}
