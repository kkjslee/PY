package com.inforstack.openstack.billing.process.result;

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
@Table(name="billing_process_result")
public class BillingProcessResult {

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BillingProcess getBillingProcess() {
		return billingProcess;
	}

	public void setBillingProcess(BillingProcess billingProcess) {
		this.billingProcess = billingProcess;
	}

	public BigDecimal getInvoiceTotal() {
		return invoiceTotal;
	}

	public void setInvoiceTotal(BigDecimal invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}

	public BigDecimal getPaidTotal() {
		return paidTotal;
	}

	public void setPaidTotal(BigDecimal paidTotal) {
		this.paidTotal = paidTotal;
	}

	public BigDecimal getUnPaidTotal() {
		return unPaidTotal;
	}

	public void setUnPaidTotal(BigDecimal unPaidTotal) {
		this.unPaidTotal = unPaidTotal;
	}

	public Integer getOrderTotal() {
		return OrderTotal;
	}

	public void setOrderTotal(Integer orderTotal) {
		OrderTotal = orderTotal;
	}
	
}
