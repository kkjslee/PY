package com.inforstack.openstack.payment.account;

import com.inforstack.openstack.basic.BasicDao;

public interface AccountDao extends BasicDao<Account> {

	public Account findActiveAccount(Integer tenantId, Integer instanceId);

}
