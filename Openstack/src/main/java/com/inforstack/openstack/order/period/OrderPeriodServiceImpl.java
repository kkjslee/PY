package com.inforstack.openstack.order.period;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.i18n.I18n;
import com.inforstack.openstack.i18n.I18nService;
import com.inforstack.openstack.i18n.dict.DictionaryService;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.MapUtil;

@Service
@Transactional
public class OrderPeriodServiceImpl implements OrderPeriodService {
	
	private static final Logger log = new Logger(OrderPeriodServiceImpl.class);
	@Autowired
	private OrderPeriodDao periodDao;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private I18nService i18nService;
	
	@Override
	public OrderPeriod createPeriod(OrderPeriod period) {
		log.debug("Create period");
		periodDao.persist(period);
		log.debug("Create successfully");
		
		return period;
	}

	@Override
	public OrderPeriod createPeriod(Map<Integer, String> nameMap, int type,
			int quantity) {
		boolean valid = dictionaryService.contains(Constants.DICTIONARY_KEY_PERIOD_TYPE, type+"");
		if(valid==false){
			log.info("Create period failed for passed type is invalid");
			return null;
		}
		List<I18n> i18n = i18nService.createI18n(nameMap, Constants.TABLE_PERIOD, Constants.COLUMN_PERIOD_NAME);
		if(CollectionUtil.isNullOrEmpty(i18n)){
			log.warn("Create period failed for create i18n for name failed");
			return null;
		}
		
		OrderPeriod period  = new OrderPeriod();
		period.setDeleted(false);
		period.setName(i18n.get(0).getI18nLink());
		period.setPeriodQuantity(quantity);
		period.setPeriodType(type);
		periodDao.persist(period);
		
		log.debug("Create period successfully");
		return period;
	}

	@Override
	public OrderPeriod deletePeriod(OrderPeriod period) {
		log.debug("Delete period with id : " + period.getId());
		period.setDeleted(true);
		log.debug("Delete period successfully");
		
		return period;
	}

	@Override
	public OrderPeriod deletePeriod(int periodId) {
		log.debug("Delete period with id : " + periodId);
		OrderPeriod period = periodDao.findById(periodId);
		if(period == null){
			log.info("Delete period failed for no period found by id : " + periodId);
			return null;
		}
		period.setDeleted(true);
		log.debug("Delete period successfully");
		
		return period;
	}

	@Override
	public List<OrderPeriod> listAll(boolean includeDeleted) {
		log.debug("List all period(s) " + (includeDeleted?" include deleted " : " exclude deleted") );
		
		List<OrderPeriod> pLst = periodDao.findAll(includeDeleted);
		if(CollectionUtil.isNullOrEmpty(pLst)){
			log.debug("No record found");
			return new ArrayList<OrderPeriod>();
		}else{
			log.debug("List all successfully");
			for (OrderPeriod period : pLst) {
				period.getName().getId();
			}
			return pLst;
		}
	}

	@Override
	public OrderPeriod findPeriodById(int periodId) {
		log.info("Find period by id : " + periodId);
		OrderPeriod period = periodDao.findById(periodId);
		if(period == null){
			log.debug("Find period by id failed for no instance found");
		}else{
			period.getName().getId();
			log.debug("Find period successfully");
		}
		
		return period;
	}

}
