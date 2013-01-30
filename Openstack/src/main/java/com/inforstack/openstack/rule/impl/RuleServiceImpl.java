package com.inforstack.openstack.rule.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.rule.Rule;
import com.inforstack.openstack.rule.RuleService;
import com.inforstack.openstack.rule.RuleType;
import com.inforstack.openstack.utils.db.AbstractDao;

@Service
@Transactional
public class RuleServiceImpl implements RuleService {

	private static final Log log = LogFactory.getLog(RuleServiceImpl.class);
	
	@Autowired
	private AbstractDao<Rule> ruleDao;
	
	@Autowired
	private AbstractDao<RuleType> ruleTypeDao;
	
	@Override
	public List<RuleType> listRuleType() {
		return ruleTypeDao.list();
	}

}
