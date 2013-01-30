package com.inforstack.openstack.item.impl;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.item.Price;
import com.inforstack.openstack.item.PriceDao;
import com.inforstack.openstack.utils.db.AbstractDao;

@Repository
public class PriceDaoImpl extends AbstractDao<Price> implements PriceDao {

	@Override
	public Price findByItemSpecificationId(int id) {
		return this.findByObject("item_id", id);
	}

}
