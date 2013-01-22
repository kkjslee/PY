package com.inforstack.openstack.period;

import java.util.List;
import java.util.Map;

public interface PeriodService {
	
	/**
	 * 
	 * @param period managed period instance
	 * @return
	 */
	public Period createPeriod(Period period); 
	
	public Period createPeriod(Map<Integer, String> nameMap, Integer type, Integer quantity);
	
	public Period deletePeriod(Period period);
	
	public Period deletePeriod(Integer periodId);
	
	public List<Period> listAll(boolean includeDeleted);
	
	public Period findPeriodById(Integer periodId);
}
