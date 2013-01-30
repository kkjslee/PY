package com.inforstack.openstack.rule.impl;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.rule.Rule;
import com.inforstack.openstack.rule.RuleDao;

@Repository
public class RuleDaoImpl extends BasicDaoImpl<Rule> implements RuleDao {

}
