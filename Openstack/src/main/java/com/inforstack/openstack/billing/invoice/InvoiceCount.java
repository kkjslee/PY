package com.inforstack.openstack.billing.invoice;

import java.math.BigDecimal;

public class InvoiceCount {
	
	private BigDecimal invoiceTotal = BigDecimal.ZERO;
	
	private BigDecimal balance = BigDecimal.ZERO;;

	public BigDecimal getInvoiceTotal() {
		return invoiceTotal;
	}

	public void setInvoiceTotal(BigDecimal invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}

	public BigDecimal addInvoiceTotal(BigDecimal augend){
		invoiceTotal = invoiceTotal.add(augend);
		return invoiceTotal;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public BigDecimal addBalance(BigDecimal augend){
		balance = balance.add(augend);
		return balance;
	}
}
