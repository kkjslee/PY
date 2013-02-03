package com.inforstack.openstack.rule;

import java.util.List;

public interface RuleService {
	
	public List<RuleType> listRuleType();
	
	public List<Rule> listRuleByTypeName(String name);
	
}
