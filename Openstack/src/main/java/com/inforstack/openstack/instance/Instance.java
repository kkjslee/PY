package com.inforstack.openstack.instance;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.inforstack.openstack.order.sub.SubOrder;

@Entity
public class Instance {

	@Id
	@GeneratedValue
	private int id;
	
	private String uuid;
	
	private String type;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional=false)
	@JoinColumn(name="parent_id")
	private Instance parent;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	private List<Instance> subInstance;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional=false)
	@JoinColumn(name="sub_order_id")
	private SubOrder subOrder;
	
	@Column(name="create_time")
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Instance getParent() {
		return parent;
	}

	public void setParent(Instance parent) {
		this.parent = parent;
	}

	public List<Instance> getSubInstance() {
		return subInstance;
	}

	public void setSubInstance(List<Instance> subInstance) {
		this.subInstance = subInstance;
	}

	public SubOrder getSubOrder() {
		return subOrder;
	}

	public void setSubOrder(SubOrder subOrder) {
		this.subOrder = subOrder;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
