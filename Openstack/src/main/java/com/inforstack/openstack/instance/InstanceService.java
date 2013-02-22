package com.inforstack.openstack.instance;

import com.inforstack.openstack.api.nova.server.Server;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.user.User;

public interface InstanceService {
	
	public void createVM(User user, Tenant tenant, String orderId);
	
	public void updateVM(User user, Tenant tenant, Server server);
	
	public void removeVM(User user, Tenant tenant, Server server);
	
}
