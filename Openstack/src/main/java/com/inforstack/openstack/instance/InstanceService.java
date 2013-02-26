package com.inforstack.openstack.instance;

import java.util.List;

import com.inforstack.openstack.order.sub.SubOrder;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.user.User;

public interface InstanceService {
	
	public SubOrder findSubOrderFromInstance(String instanceId);
	
	public List<Instance> findInstanceFromTenant(Tenant tenant);
	
	public Instance findInstanceFromUUID(String uuid);
	
	public List<VirtualMachine> findVirtualMachineFromTenant(Tenant tenant);
	
	public VirtualMachine findVirtualMachineFromUUID(String uuid);
	
	public void createVM(User user, Tenant tenant, String orderId);
	
	public void updateVM(User user, Tenant tenant, String serverId, String name);
	
	public void removeVM(User user, Tenant tenant, String serverId, boolean freeVolumeAndIP);
	
}
