package com.inforstack.openstack.item;

public interface PriceDao {
	
	public Price findById(int id);
	
	public Price findByItemSpecificationId(int id);
	
	public Price persist(Price price);
	
	public void update(Price price);
	
	public void remove(Price price);
	
	public void remove(int id);

}
