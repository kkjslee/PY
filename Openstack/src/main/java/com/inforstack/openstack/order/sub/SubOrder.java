package com.inforstack.openstack.order.sub;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.inforstack.openstack.instance.Instance;
import com.inforstack.openstack.item.ItemSpecification;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.order.period.OrderPeriod;
import com.inforstack.openstack.virt.domain.VirtDomain;

@Entity
@Table(name="sub_order")
public class SubOrder {

	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer type;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="item_id")
	private ItemSpecification item;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private Order order;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="order_period_id")
	private OrderPeriod orderPeriod;
	
	@Column(name="next_billing_date")
	private Date nextBillingDate;
	
	private BigDecimal price; 
	
	private int status;
	
	private String uuid;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="instance_id")
	private Instance instance;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ItemSpecification getItem() {
		return item;
	}

	public void setItem(ItemSpecification item) {
		this.item = item;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public OrderPeriod getOrderPeriod() {
		return orderPeriod;
	}

	public void setOrderPeriod(OrderPeriod orderPeriod) {
		this.orderPeriod = orderPeriod;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getNextBillingDate() {
		return nextBillingDate;
	}

	public void setNextBillingDate(Date nextBillingDate) {
		this.nextBillingDate = nextBillingDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Instance getInstance() {
		return instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}
	
}
