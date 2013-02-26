package com.inforstack.openstack.instance.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.cinder.CinderService;
import com.inforstack.openstack.api.cinder.Volume;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.nova.server.Server;
import com.inforstack.openstack.api.nova.server.ServerService;
import com.inforstack.openstack.instance.Instance;
import com.inforstack.openstack.instance.InstanceDao;
import com.inforstack.openstack.instance.InstanceService;
import com.inforstack.openstack.instance.InstanceStatusDao;
import com.inforstack.openstack.instance.UserActionDao;
import com.inforstack.openstack.instance.VirtualMachine;
import com.inforstack.openstack.instance.VirtualMachineDao;
import com.inforstack.openstack.instance.VolumeInstance;
import com.inforstack.openstack.instance.VolumeInstanceDao;
import com.inforstack.openstack.item.ItemSpecification;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.order.OrderService;
import com.inforstack.openstack.order.sub.SubOrder;
import com.inforstack.openstack.order.sub.SubOrderService;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.utils.Constants;

@Service("instanceService")
@Transactional
public class InstanceServiceImpl implements InstanceService {
	
	@Autowired
	private InstanceDao instanceDao;
	
	@Autowired
	private VirtualMachineDao virtualMachineDao;
	
	@Autowired
	private VolumeInstanceDao volumeInstanceDao;
	
	@Autowired
	private UserActionDao userActionDao;
	
	@Autowired
	private InstanceStatusDao instanceStatusDao;
	
	@Autowired
	private KeystoneService keystoneService;
	
	@Autowired
	private ServerService serverService;
	
	@Autowired
	private CinderService cinderService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SubOrderService subOrderService;
	
	@Override
	public SubOrder findSubOrderFromInstance(String instanceId) {
		SubOrder subOrder = null;
		if (instanceId != null && !instanceId.trim().isEmpty()) {
			Instance instance = this.instanceDao.findById(instanceId);
			if (instance != null) {
				subOrder = instance.getSubOrder();
			}
		}
		return subOrder;
	}
	
	@Override
	public List<Instance> findInstanceFromTenant(Tenant tenant, String includeStatus, String excludeStatus) {
		List<Instance> instanceList = new ArrayList<Instance>();
		List<Order> orderList = this.orderService.findAll(tenant.getId(), Constants.ORDER_STATUS_ACTIVE);
		for (Order order : orderList) {
			List<SubOrder> subOrders = order.getSubOrders();
			for (SubOrder subOrder : subOrders) {
				List<Instance> instances = this.instanceDao.listInstancesBySubOrder(subOrder.getId(), includeStatus, excludeStatus);
				instanceList.addAll(instances);
			}
		}
		return instanceList;
	} 
	
	@Override
	public Instance findInstanceFromUUID(String uuid) {
		Instance instance = this.instanceDao.findByObject("uuid", uuid);
		return instance;
	}
	
	@Override
	public List<VirtualMachine> findVirtualMachineFromTenant(Tenant tenant, String includeStatus, String excludeStatus) {
		List<VirtualMachine> virtualMachineList = new ArrayList<VirtualMachine>();
		List<Instance> instances = this.instanceDao.listInstancesByTenant(tenant, includeStatus, excludeStatus);
		for (Instance instance : instances) {
			if (instance.getType() == Constants.INSTANCE_TYPE_VM && (includeStatus == null || instance.getStatus().equalsIgnoreCase(includeStatus))) {
				virtualMachineList.add(this.virtualMachineDao.findByObject("uuid", instance.getUuid()));
			}
		}
		return virtualMachineList;
	}
	
	@Override
	public VirtualMachine findVirtualMachineFromUUID(String uuid) {
		return this.virtualMachineDao.findByObject("uuid", uuid);
	}

	@Override
	public void createVM(User user, Tenant tenant, String orderId) {
		if (orderId != null && !orderId.trim().isEmpty()) {
			Access access = null;
			Server newServer = null;
			Volume newVolume = null;
			try {
				access = this.keystoneService.getAccess(user.getUsername(), user.getPassword(), tenant.getUuid(), true);
				if (access != null) {
					Order order = this.orderService.findOrderById(orderId);
					if (order != null) {						
						Server server = this.getServerFromOrder(order);
						if (server != null) {
							newServer = this.serverService.createServer(access, server);
							if (newServer != null) {
								this.bindServerToSubOrder(newServer, order);
								server = newServer;
							}
						}
						
						Volume volume = this.getVolumeFromOrder(order);
						if (volume != null && volume.getType() != null && !volume.getType().isEmpty()) {
							newVolume = new Volume();//this.cinderService.createVolume(access, volume.getName(), volume.getDescription(), vo, bootable, type, zone)
							if (newVolume != null) {
								this.bindVolumeToSubOrder(newVolume, order);
								if (server.getId() != null && !server.getId().isEmpty()) {
									// TODO: [ricky]attach volume after server started
								}
							}
						}
					}
				}
			} catch (OpenstackAPIException e) {
				throw new RuntimeException(e);
			} finally {
				// TODO: [ricky]remove openstack instances after error
				if (access != null) {
					
				}
			}
		}
	}

	@Override
	public void updateVM(User user, Tenant tenant, String serverId, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeVM(User user, Tenant tenant, String serverId, boolean freeVolumeAndIP) {
		// TODO Auto-generated method stub
		
	}
	
	private void registerInstance(int type, String id, String name, SubOrder subOrder) {
		Date now = new Date();
		Instance instance = new Instance();
		instance.setType(type);
		instance.setUuid(id);
		instance.setName(name);
		instance.setSubOrder(subOrder);
		instance.setCreateTime(now);
		instance.setUpdateTime(now);
		instance.setStatus("new");
		instance.setTask("");
		
		this.instanceDao.persist(instance);
	}
	
	private Server getServerFromOrder(Order order) {
		Server server = null;
		String imageRef = null;
		String flavorRef = null;
		List<SubOrder> subOrders = order.getSubOrders();
		for (SubOrder subOrder : subOrders) {
			int osType = subOrder.getItem().getOsType();
			String refId = subOrder.getItem().getRefId();
			switch (osType) {
			case ItemSpecification.OS_TYPE_IMAGE_ID:
				imageRef = refId;
				break;
			case ItemSpecification.OS_TYPE_FLAVOR_ID:
				flavorRef = refId;
				break;
			}
		}
		if (imageRef != null && flavorRef != null) {
			server = new Server();
			server.setName("New Instance");
			server.setImageRef(imageRef);
			server.setFlavorRef(flavorRef);
		}
		return server;
	}
	
	private Volume getVolumeFromOrder(Order order) {
		Volume volume = new Volume();
		List<SubOrder> subOrders = order.getSubOrders();
		for (SubOrder subOrder : subOrders) {
			int osType = subOrder.getItem().getOsType();
			if (osType == ItemSpecification.OS_TYPE_VOLUME_ID) {
				String refId = subOrder.getItem().getRefId();
				volume.setType(refId);
				break;
			}
		}
		return volume;
	}
	
	private void bindServerToSubOrder(Server server, Order order) {
		List<SubOrder> subOrders = order.getSubOrders();
		for (SubOrder subOrder : subOrders) {
			int osType = subOrder.getItem().getOsType();
			switch (osType) {
			case ItemSpecification.OS_TYPE_PERIOD_ID:
				VirtualMachine vm = new VirtualMachine();
				vm.setUuid(server.getId());
				vm.setName(server.getName());
				vm.setImage(server.getImage().getId());
				vm.setFlavor(server.getFlavor().getId());
				this.virtualMachineDao.persist(vm);
				this.registerInstance(Constants.INSTANCE_TYPE_VM, server.getId(), server.getName(), subOrder);
			case ItemSpecification.OS_TYPE_FLAVOR_ID:
			case ItemSpecification.OS_TYPE_IMAGE_ID:
				subOrder.setUuid(server.getId());
				break;
			}
		}
	}
	
	private void bindVolumeToSubOrder(Volume volume, Order order) {
		List<SubOrder> subOrders = order.getSubOrders();
		for (SubOrder subOrder : subOrders) {
			int osType = subOrder.getItem().getOsType();
			switch (osType) {
			case ItemSpecification.OS_TYPE_VOLUME_ID:
				subOrder.setUuid(volume.getId());
				this.registerInstance(Constants.INSTANCE_TYPE_VOLUME, volume.getId(), volume.getName(), subOrder);
				VolumeInstance vi = new VolumeInstance();
				vi.setSize(volume.getSize());
				break;
			}
		}
	}
	
//	private void bindNetworkToSubOrder(Network network, Order order) {
//		List<SubOrder> subOrders = order.getSubOrders();
//		for (SubOrder subOrder : subOrders) {
//			int osType = subOrder.getItem().getOsType();
//			switch (osType) {
//			case ItemSpecification.OS_TYPE_NETWORK_ID:
//				subOrder.setUuid(network.getId());
//				this.registerInstance(Constants.INSTANCE_TYPE_IP, network.getId(), subOrder);
//				break;
//			}
//		}
//	}

}
