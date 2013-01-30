package com.inforstack.openstack.promotion;

import java.util.List;

import com.inforstack.openstack.basic.BasicDao;
import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.tenant.Tenant;

public interface PromotionDao extends BasicDao<Promotion> {

	public Promotion findByNameAndTenant(String name, Tenant tenant) throws ApplicationException;
	
	public Promotion findByNameAndRole(String name, int roleId) throws ApplicationException;
	
	public List<Promotion> findAllByName(String name);
	
	public boolean exist(String name) throws ApplicationException;


}
