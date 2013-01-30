package com.inforstack.openstack.item;

import com.inforstack.openstack.basic.BasicDao;

public interface PriceDao extends BasicDao<Price> {
	
	public Price findByItemSpecificationId(int id);

}
