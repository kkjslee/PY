package com.inforstack.openstack.billing.process.report;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.inforstack.openstack.billing.process.BillingProcess;

@Entity
@Table(name="billing_process_report")
public class BillingProcessReport {

	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="billing_process_id")
	private BillingProcess billingProcess;
	
	@Column(name="invoice_total")
	private BigDecimal invoiceTotal;
	
	@Column(name="paid_total")
	private BigDecimal paidTotal;
	
	@Column(name="unpaid_total")
	private BigDecimal unPaidTotal;
	
	@Column(name="order_total")
	private Integer OrderTotal;
}
