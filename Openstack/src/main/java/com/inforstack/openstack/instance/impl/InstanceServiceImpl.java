package com.inforstack.openstack.instance.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.cinder.Volume;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.nova.server.Server;
import com.inforstack.openstack.api.nova.server.ServerService;
import com.inforstack.openstack.api.quantum.Network;
import com.inforstack.openstack.instance.InstanceService;
import com.inforstack.openstack.instance.InstanceStatusDao;
import com.inforstack.openstack.instance.UserActionDao;
import com.inforstack.openstack.item.ItemSpecification;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.order.OrderService;
import com.inforstack.openstack.order.sub.SubOrder;
import com.inforstack.openstack.order.sub.SubOrderService;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.utils.OpenstackUtil;

@Service("instanceService")
@Transactional
public class InstanceServiceImpl implements InstanceService {
	
	@Autowired
	private UserActionDao userActionDao;
	
	@Autowired
	private InstanceStatusDao instanceStatusDao;
	
	@Autowired
	private KeystoneService keystoneService;
	
	@Autowired
	private ServerService serverService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SubOrderService subOrderService;

	@Override
	public void createVM(User user, Tenant tenant, String orderId) {
		if (orderId != null && !orderId.trim().isEmpty()) {
			try {
				Access access = this.keystoneService.getAccess(user.getUsername(), user.getPassword(), tenant.getUuid(), true);
				if (access != null) {
					Order order = this.orderService.findOrderById(orderId);
					if (order != null) {
						//InstanceService self = (InstanceService) OpenstackUtil.getBean("instanceService");
						Server server = this.getServerFromOrder(order);
						Server newServer = this.serverService.createServer(access, server);
						if (newServer != null) {
							this.bindServerToSubOrder(newServer, order);
						}
					}
				}
			} catch (OpenstackAPIException e) {
			}
		}
	}

	@Override
	public void updateVM(User user, Tenant tenant, Server server) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeVM(User user, Tenant tenant, Server server) {
		// TODO Auto-generated method stub
		
	}
	
	private Server getServerFromOrder(Order order) {
		Server server = new Server();
		server.setName("New Instance");
		List<SubOrder> subOrders = order.getSubOrders();
		for (SubOrder subOrder : subOrders) {
			int osType = subOrder.getItem().getOsType();
			String refId = subOrder.getItem().getRefId();
			switch (osType) {
			case ItemSpecification.OS_TYPE_IMAGE_ID:
				server.setImageRef(refId);
				break;
			case ItemSpecification.OS_TYPE_FLAVOR_ID:
				server.setFlavorRef(refId);
				break;
			}
		}
		return server;
	}
	
	private void bindServerToSubOrder(Server server, Order order) {
		List<SubOrder> subOrders = order.getSubOrders();
		for (SubOrder subOrder : subOrders) {
			int osType = subOrder.getItem().getOsType();
			String refId = subOrder.getItem().getRefId();
			switch (osType) {
			case ItemSpecification.OS_TYPE_IMAGE_ID:
			case ItemSpecification.OS_TYPE_FLAVOR_ID:
				subOrder.setUuid(server.getId());
				break;
			}
		}
	}
	
	private void bindVolumeToSubOrder(Volume volume, Order order) {
		List<SubOrder> subOrders = order.getSubOrders();
		for (SubOrder subOrder : subOrders) {
			int osType = subOrder.getItem().getOsType();
			String refId = subOrder.getItem().getRefId();
			switch (osType) {
			case ItemSpecification.OS_TYPE_VOLUME_ID:
				subOrder.setUuid(volume.getId());
				break;
			}
		}
	}
	
	private void bindNetworkToSubOrder(Network network, Order order) {
		List<SubOrder> subOrders = order.getSubOrders();
		for (SubOrder subOrder : subOrders) {
			int osType = subOrder.getItem().getOsType();
			String refId = subOrder.getItem().getRefId();
			switch (osType) {
			case ItemSpecification.OS_TYPE_NETWORK_ID:
				subOrder.setUuid(network.getId());
				break;
			}
		}
	}

}
