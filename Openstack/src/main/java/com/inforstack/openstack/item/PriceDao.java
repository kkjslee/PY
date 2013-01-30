package com.inforstack.openstack.item;

import com.inforstack.openstack.utils.db.IDao;

public interface PriceDao extends IDao<Price> {
	
	public Price findByItemSpecificationId(int id);

}
