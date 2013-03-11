package com.inforstack.openstack.payment.account;

import java.math.BigDecimal;

import com.inforstack.openstack.instance.Instance;
import com.inforstack.openstack.tenant.Tenant;

public interface AccountService {

	public Account createAccount(int tenantId, Integer instanceId);

	public Account changeAccount(Account Account, BigDecimal amount, boolean useOnly);

	public Account findAccountById(int accountId);

	public Account findActiveAccount(int tenantId, int instanceId);

	public Account findActiveAccount(Tenant tenant, Instance instance);

	public Account findOrCreateActiveAccount(Tenant tenant, Instance instance);

}
