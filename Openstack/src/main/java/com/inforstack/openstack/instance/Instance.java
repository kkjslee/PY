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
	
	private int type;
	
	private String uuid;
	
	private String name;
	
	private String status;
	
	private String task;
			
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional=true)
	@JoinColumn(name="parent_id")
	private Instance parent;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	private List<Instance> subInstance;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional=true)
	@JoinColumn(name="sub_order_id")
	private SubOrder subOrder;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
