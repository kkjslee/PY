package com.inforstack.openstack.rule.impl;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.rule.RuleType;
import com.inforstack.openstack.rule.RuleTypeDao;

@Repository
public class RuleTypeDaoImpl extends BasicDaoImpl<RuleType> implements RuleTypeDao {

}
