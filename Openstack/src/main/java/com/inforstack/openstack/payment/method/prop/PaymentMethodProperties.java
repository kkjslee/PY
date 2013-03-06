package com.inforstack.openstack.payment.method.prop;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.inforstack.openstack.payment.method.PaymentMethod;

@Entity
@Table(name="payment_method_properties")
public class PaymentMethodProperties {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn
	private PaymentMethod method;
}
