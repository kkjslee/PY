package com.inforstack.openstack.billing.process.conf;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="billing_process_conf")
public class BillingProcessConfiguration {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	@Column(name="next_billing_date")
	private Date nextBillingDate;
	
	private Integer retry;
	
	@Column(name="days_between_retry")
	private Integer daysBetweenRetry;
	
	@Column(name="period_type")
	private int periodType;
	
	@Column(name="period_quantity")
	private int periodQuantity;
	
	@Column(name="create_time")
	private Date createTime;

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

	public Integer getRetry() {
		return retry;
	}

	public void setRetry(Integer retry) {
		this.retry = retry;
	}

	public Integer getDaysBetweenRetry() {
		return daysBetweenRetry;
	}

	public void setDaysBetweenRetry(Integer daysBetweenRetry) {
		this.daysBetweenRetry = daysBetweenRetry;
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
	
}
