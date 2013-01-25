package com.inforstack.openstack.payment;

public interface PaymentDao {

	public void persist(Payment payment);

	public Payment findById(Integer paymentId);

}
