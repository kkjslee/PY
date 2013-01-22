package com.inforstack.openstack.period;

import java.util.List;

public interface PeriodDao {

	public void persist(Period period);
	
	public void merge(Period period);
	
	public void remove(Period period);

	public Period findById(Integer periodId);

	public List<Period> findAll(boolean includeDeleted);
}
