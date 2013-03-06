package com.inforstack.openstack.payment.method.prop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.Constants;

@Service
@Transactional
public class PaymentMethodPropertyServiceImpl implements PaymentMethodPropertyService{
	
	@Autowired
	private PaymentMethodPropertyDao dao;
	
	@Override
	public PaymentMethodProperty findById(int paymentMethodPropertyId){
		return dao.findById(paymentMethodPropertyId);
	}
	
	@Override
	public List<PaymentMethodProperty> findParams(int paymentMethodId){
		List<PaymentMethodProperty> ret = dao.findbyMethodAndType(paymentMethodId, Constants.PAYMENTMETHODPROPERTY_TYPE_PARAM);
		if(CollectionUtil.isNullOrEmpty(ret)){
			return new ArrayList<PaymentMethodProperty>();
		}
		
		return ret;
	}
	
	@Override
	public List<PaymentMethodProperty> findProps(int paymentMethodId){
		List<PaymentMethodProperty> ret = dao.findbyMethodAndType(paymentMethodId, Constants.PAYMENTMETHODPROPERTY_TYPE_PROP);
		if(CollectionUtil.isNullOrEmpty(ret)){
			return new ArrayList<PaymentMethodProperty>();
		}
		
		return ret;
	}
	
	@Override
	public List<PaymentMethodProperty> findMethodParams(int paymentMethodId){
		List<PaymentMethodProperty> ret = dao.findbyMethodAndType(paymentMethodId, Constants.PAYMENTMETHODPROPERTY_TYPE_METHOD_PARAM);
		if(CollectionUtil.isNullOrEmpty(ret)){
			return new ArrayList<PaymentMethodProperty>();
		}
		
		return ret;
	}
}
