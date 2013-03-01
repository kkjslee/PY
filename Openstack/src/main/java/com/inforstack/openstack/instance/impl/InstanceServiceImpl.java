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
import com.inforstack.openstack.api.cinder.VolumeType;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.nova.server.Server;
import com.inforstack.openstack.api.nova.server.ServerService;
import com.inforstack.openstack.instance.AttachTaskService;
import com.inforstack.openstack.instance.AttributeMap;
import com.inforstack.openstack.instance.Instance;
import com.inforstack.openstack.instance.InstanceDao;
import com.inforstack.openstack.instance.InstanceService;
import com.inforstack.openstack.instance.InstanceStatusDao;
import com.inforstack.openstack.instance.VirtualMachine;
import com.inforstack.openstack.instance.VirtualMachineDao;
import com.inforstack.openstack.instance.VolumeInstance;
import com.inforstack.openstack.instance.VolumeInstanceDao;
import com.inforstack.openstack.item.ItemSpecification;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.order.OrderService;
import com.inforstack.openstack.order.period.OrderPeriod;
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
	
	@Autowired
	private AttachTaskService attachTaskService;
	
	@Override
	public OrderPeriod getInstancePeriod(int id) {
		SubOrder subOrder = this.subOrderService.findFirstSubOrderByInstanceId(id);
		OrderPeriod period = subOrder.getOrderPeriod();
		period.getName().getId();
		return period;
	}
	
	@Override
	public List<Instance> findInstanceFromTenant(Tenant tenant, String includeStatus, String excludeStatus) {
		List<Instance> instanceList = new ArrayList<Instance>();
		List<Order> orderList = this.orderService.findAll(tenant.getId(), null);
		for (Order order : orderList) {
			List<SubOrder> subOrders = order.getSubOrders();
			for (SubOrder subOrder : subOrders) {
				List<Instance> instances = this.instanceDao.listInstancesBySubOrder(subOrder.getId(), 0, includeStatus, excludeStatus);
				instanceList.addAll(instances);
			}
		}
		return instanceList;
	} 
	
	@Override
	public List<Instance> findInstanceFromTenant(Tenant tenant, int type, String includeStatus, String excludeStatus) {
		List<Instance> instanceList = new ArrayList<Instance>();
		List<Order> orderList = this.orderService.findAll(tenant.getId(), null);
		for (Order order : orderList) {
			List<SubOrder> subOrders = order.getSubOrders();
			for (SubOrder subOrder : subOrders) {
				List<Instance> instances = this.instanceDao.listInstancesBySubOrder(subOrder.getId(), type, includeStatus, excludeStatus);
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
		List<Instance> instances = this.instanceDao.listInstancesByTenant(tenant, Constants.INSTANCE_TYPE_VM, includeStatus, excludeStatus);
		for (Instance instance : instances) {
			virtualMachineList.add(this.virtualMachineDao.findByObject("uuid", instance.getUuid()));
		}
		return virtualMachineList;
	}
	
	@Override
	public VirtualMachine findVirtualMachineFromUUID(String uuid) {
		return this.virtualMachineDao.findByObject("uuid", uuid);
	}
	
	@Override
	public List<VolumeInstance> findVolumeFromTenant(Tenant tenant, String includeStatus, String excludeStatus) {
		List<VolumeInstance> volumeList = new ArrayList<VolumeInstance>();
		List<Instance> instances = this.instanceDao.listInstancesByTenant(tenant, Constants.INSTANCE_TYPE_VOLUME, includeStatus, excludeStatus);
		for (Instance instance : instances) {
			volumeList.add(this.volumeInstanceDao.findByObject("uuid", instance.getUuid()));
		}
		return volumeList;
	}

	@Override
	public VolumeInstance findVolumeInstanceFromUUID(String uuid) {
		return this.volumeInstanceDao.findByObject("uuid", uuid);
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
						VirtualMachine vm = null;
						VolumeInstance vi = null;
						Server server = this.getServerFromOrder(order);
						if (server != null) {
							newServer = this.serverService.createServer(access, server);
							if (newServer != null) {
								vm = this.bindServerToSubOrder(newServer, order);
								server = newServer;
							}
						}
						
						Volume volume = this.getVolumeFromOrder(order);
						if (volume != null && volume.getType() != null && !volume.getType().isEmpty()) {
							newVolume = this.cinderService.createVolume(access, volume.getName(), "", volume.getSize(), false, volume.getType(), volume.getZone());
							if (newVolume != null) {
								// TODO: [ricky] get device name from order
								String deviceName = newVolume.getDescription();
								vi = this.bindVolumeToSubOrder(newVolume, order, vm, deviceName);
								if (server.getId() != null && !server.getId().isEmpty()) {
									this.attachTaskService.addTask(Constants.ATTACH_TASK_TYPE_VOLUME, vm.getUuid(), vi.getUuid(), deviceName, user.getUsername(), user.getPassword(), tenant.getUuid());
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
	}

	@Override
	public void removeVM(User user, Tenant tenant, String serverId, boolean freeVolumeAndIP) {
	}
	
	private Instance registerInstance(int type, String id, String name) {
		Date now = new Date();
		Instance instance = new Instance();
		instance.setType(type);
		instance.setUuid(id);
		instance.setName(name);
		instance.setCreateTime(now);
		instance.setUpdateTime(now);
		instance.setStatus("new");
		instance.setTask("");
		
		this.instanceDao.persist(instance);
		return instance;
	}
	
	private Server getServerFromOrder(Order order) {
		Server server = null;
		String imageRef = null;
		String flavorRef = null;
		String serverName = "New Instance";
		List<SubOrder> subOrders = order.getSubOrders();
		for (SubOrder subOrder : subOrders) {
			int osType = subOrder.getItem().getOsType();
			String refId = subOrder.getItem().getRefId();
			switch (osType) {
				case ItemSpecification.OS_TYPE_IMAGE_ID: {
					imageRef = refId;
					String name = AttributeMap.getInstance().get(subOrder.getId(), "name");
					if (name != null) {
						serverName = name;
					}
					break;
				}
				case ItemSpecification.OS_TYPE_FLAVOR_ID: {
					flavorRef = refId;
					String name = AttributeMap.getInstance().get(subOrder.getId(), "name");
					if (name != null) {
						serverName = name;
					}
					break;
				}
			}
		}
		if (imageRef != null && flavorRef != null) {
			server = new Server();
			server.setName(serverName);
			server.setImageRef(imageRef);
			server.setFlavorRef(flavorRef);
		}
		return server;
	}
	
	private Volume getVolumeFromOrder(Order order) {
		Volume volume = null;
		
		VolumeType vt = null;
		String volumeName = "New Volume";
		String description = "/dev/test";
		List<SubOrder> subOrders = order.getSubOrders();
		for (SubOrder subOrder : subOrders) {
			int osType = subOrder.getItem().getOsType();
			if (osType == ItemSpecification.OS_TYPE_VOLUME_ID) {
				String refId = subOrder.getItem().getRefId();
				try {
					vt = this.cinderService.getVolumeType(refId);
				} catch (OpenstackAPIException e) {
				}
				String name = AttributeMap.getInstance().get(subOrder.getId(), "name");
				if (name != null) {
					volumeName = name;
				}
				String extra = AttributeMap.getInstance().get(subOrder.getId(), "extra");
				if (extra != null) {
					description = extra;
				}
				break;
			}
		}
		
		if (vt != null) {
			volume = new Volume();
			volume.setName(volumeName);
			volume.setType(vt.getId());
			volume.setDescription(description);
			// TODO: 
			//volume.setSize(Integer.parseInt(vt.getName()));
			volume.setSize(1);
			// TODO: set zone from order
			volume.setZone("nova");
		}
		return volume;
	}
	
	private VirtualMachine bindServerToSubOrder(Server server, Order order) {
		VirtualMachine vm = null;
		List<SubOrder> subOrders = order.getSubOrders();
		for (SubOrder subOrder : subOrders) {
			int osType = subOrder.getItem().getOsType();
			switch (osType) {
			case ItemSpecification.OS_TYPE_PERIOD_ID:
				vm = new VirtualMachine();
				vm.setUuid(server.getId());
				vm.setName(server.getName());
				vm.setImage(server.getImage().getId());
				vm.setFlavor(server.getFlavor().getId());
				this.virtualMachineDao.persist(vm);
				Instance instance = this.registerInstance(Constants.INSTANCE_TYPE_VM, server.getId(), server.getName());
				subOrder.setInstance(instance);
			case ItemSpecification.OS_TYPE_FLAVOR_ID:
			case ItemSpecification.OS_TYPE_IMAGE_ID:
				subOrder.setUuid(server.getId());
				break;
			}
		}
		return vm;
	}
	
	private VolumeInstance bindVolumeToSubOrder(Volume volume, Order order, VirtualMachine vm, String device) {
		VolumeInstance vi = null;
		List<SubOrder> subOrders = order.getSubOrders();
		for (SubOrder subOrder : subOrders) {
			int osType = subOrder.getItem().getOsType();
			switch (osType) {
			case ItemSpecification.OS_TYPE_VOLUME_ID:
				subOrder.setUuid(volume.getId());
				vi = new VolumeInstance();
				vi.setUuid(volume.getId());
				vi.setName(volume.getName());
				vi.setSize(volume.getSize());
				if (vm != null && device != null) {
					vi.setVm(vm.getUuid());
					vi.setDevice(device);
				}
				this.volumeInstanceDao.persist(vi);
				Instance instance = this.registerInstance(Constants.INSTANCE_TYPE_VOLUME, volume.getId(), volume.getName());
				subOrder.setInstance(instance);
				break;
			}
		}
		return vi;
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
