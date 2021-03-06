package com.inforstack.openstack.instance.impl;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.instance.UserAction;
import com.inforstack.openstack.instance.UserActionDao;

@Repository
public class UserActionDaoImpl extends BasicDaoImpl<UserAction> implements UserActionDao {

}
