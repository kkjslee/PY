package com.inforstack.openstack.rule.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.rule.RuleDao;
import com.inforstack.openstack.rule.RuleService;
import com.inforstack.openstack.rule.RuleType;
import com.inforstack.openstack.rule.RuleTypeDao;

@Service
@Transactional
public class RuleServiceImpl implements RuleService {

	private static final Logger log = new Logger(RuleServiceImpl.class);
	
	@Autowired
	private RuleDao ruleDao;
	
	@Autowired
	private RuleTypeDao ruleTypeDao;
	
	@Override
	public List<RuleType> listRuleType() {
		return ruleTypeDao.list();
	}

}
