package com.inforstack.openstack.item;

public interface ProfileDao {

	public Profile findById(int id);
	
	public Profile persist(Profile profile);
	
	public void update(Profile profile);
	
	public void remove(Profile profile);
	
	public void remove(int id);
	
}
