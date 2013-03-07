package com.inforstack.openstack.billing.process.conf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.inforstack.openstack.order.period.OrderPeriod;

@Entity
@Table(name="billing_process_conf")
public class BillingProcessConfiguration {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	@Column(name="next_billing_date")
	private Date nextBillingDate;
	
	@Column(name="period_type")
	private int periodType;
	
	@Column(name="period_quantity")
	private int periodQuantity;
	
	@Column(name="create_time")
	private Date createTime;
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY, mappedBy="processConfig")
	private List<OrderPeriod> orderPeriods = new ArrayList<OrderPeriod>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getNextBillingDate() {
		return nextBillingDate;
	}

	public void setNextBillingDate(Date nextBillingDate) {
		this.nextBillingDate = nextBillingDate;
	}

	public int getPeriodType() {
		return periodType;
	}

	public void setPeriodType(int periodType) {
		this.periodType = periodType;
	}

	public int getPeriodQuantity() {
		return periodQuantity;
	}

	public void setPeriodQuantity(int periodQuantity) {
		this.periodQuantity = periodQuantity;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<OrderPeriod> getOrderPeriods() {
		return orderPeriods;
	}

	public void setOrderPeriods(List<OrderPeriod> orderPeriods) {
		this.orderPeriods = orderPeriods;
	}
}
