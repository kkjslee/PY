package com.inforstack.openstack.instance;

import java.util.List;

import com.inforstack.openstack.item.DataCenter;
import com.inforstack.openstack.order.period.OrderPeriod;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.user.User;

public interface InstanceService {
	
	public DataCenter getDataCenterFromInstance(Instance instance);
	
	public OrderPeriod getPeriodFromInstance(Instance stance);
	
	public List<Instance> findInstanceFromTenant(Tenant tenant, String includeStatus, String excludeStatus);
	
	public List<Instance> findInstanceFromTenant(Tenant tenant, int type, String includeStatus, String excludeStatus);
	
	public Instance findInstanceFromUUID(String uuid);
	
	public List<VirtualMachine> findVirtualMachineFromTenant(Tenant tenant, String includeStatus, String excludeStatus);
	
	public VirtualMachine findVirtualMachineFromUUID(String uuid);
	
	public List<VolumeInstance> findVolumeFromTenant(Tenant tenant, String includeStatus, String excludeStatus);
	
	public VolumeInstance findVolumeInstanceFromUUID(String uuid);
	
	public void createVM(User user, Tenant tenant, String orderId);
	
	public void updateVM(User user, Tenant tenant, String serverId, String name);
	
	public void removeVM(User user, Tenant tenant, String serverId, boolean freeVolumeAndIP);

	public Instance findInstanceById(Integer instanceId);
	
}
