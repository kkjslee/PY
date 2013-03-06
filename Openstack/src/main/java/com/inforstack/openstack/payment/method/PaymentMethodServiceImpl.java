package com.inforstack.openstack.payment.method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.payment.method.prop.PaymentMethodProperty;
import com.inforstack.openstack.payment.method.prop.PaymentMethodPropertyService;
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.StringUtil;

@Service
@Transactional
public class PaymentMethodServiceImpl implements PaymentMethodService {

	private static final Logger log = new Logger(PaymentMethodServiceImpl.class);
	@Autowired
	private PaymentMethodDao paymentMethodDao;
	@Autowired
	private PaymentMethodPropertyService paymentMethodPropertyService;
	
	@Override
	public PaymentMethod findPaymentMethodByType(int type) {
		log.debug("Find payment method by type : " + type);
		PaymentMethod paymentMethod = paymentMethodDao.findByObject("type", type);
		log.debug("Find payment method successfully");
		
		return paymentMethod;
	}
	
	@Override
	public List<PaymentMethod> listAll(){
		return paymentMethodDao.listAll();
	}
	
	@Override
	public List<PaymentMethodProperty> findParams(int paymentMethodId, double price){
		PaymentMethod pm =paymentMethodDao.findById(paymentMethodId);
		if(pm == null){
			log.error("No payment method found by id : " + paymentMethodId);
			throw new ApplicationRuntimeException("No payment method found by id : " + paymentMethodId);
		}
		
		PaymentMethodPropertyService pmps = paymentMethodPropertyService;
		List<PaymentMethodProperty> props = pmps.findProps(paymentMethodId);
		Map<String, String> propMap = CollectionUtil.collectionToMap(props, "name", "value");
		propMap.put(Constants.PAYMENTMETHODPROPERTY_NAME_PRICE, "" + price);
		List<PaymentMethodProperty> params = pmps.findParams(paymentMethodId);
		params = buildParams(params, propMap);
		List<PaymentMethodProperty> methodParams = pmps.findMethodParams(paymentMethodId);
		methodParams = buildParams(methodParams, params, propMap);
		params.addAll(methodParams);
		
		return params;
	}

	private List<PaymentMethodProperty> buildParams(
			List<PaymentMethodProperty> params, Map<String, String> propMap) {
		if(CollectionUtil.isNullOrEmpty(params)){
			return new ArrayList<PaymentMethodProperty>();
		}
		
		for(PaymentMethodProperty param : params){
			param.setValue(OpenstackUtil.setProperty(param.getValue(), propMap));
		}
		
		return params;
	}
	
	private List<PaymentMethodProperty> buildParams(
			List<PaymentMethodProperty> methodParams,
			List<PaymentMethodProperty> params, Map<String, String> propMap) {
		if(CollectionUtil.isNullOrEmpty(methodParams)){
			return new ArrayList<PaymentMethodProperty>();
		}
		
		for(PaymentMethodProperty mp : methodParams){
			String value = mp.getValue();
			if(StringUtil.isNullOrEmpty(value) == false){
				int index = value.lastIndexOf(".");
				if(index != -1){
					try{
						String clz = value.substring(0, index);
						String method = value.substring(index + 1);
						Class<?> clazz = Class.forName(clz);
						value = clazz.getMethod(method, List.class, Map.class).invoke(clazz.newInstance(), params, propMap).toString();
					}catch(Exception e){
						throw new ApplicationRuntimeException(e);
					}
				}else{
					throw new ApplicationRuntimeException("Error payment method configuration : " + mp.getId());
				}
			}else{
				value = "";
			}
			
			mp.setValue(value);
		}
		
		return params;
	}
}
