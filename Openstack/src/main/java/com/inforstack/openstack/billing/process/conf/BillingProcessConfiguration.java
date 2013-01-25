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
	
	@Column(name="order_period_id")
	private Integer orderPeriodId;
	
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
	
}
