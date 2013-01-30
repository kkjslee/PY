package com.inforstack.openstack.item.impl;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.item.Price;
import com.inforstack.openstack.item.PriceDao;

@Repository
public class PriceDaoImpl extends BasicDaoImpl<Price> implements PriceDao {

	@Override
	public Price findByItemSpecificationId(int id) {
		return this.findByObject("item_id", id);
	}

}
