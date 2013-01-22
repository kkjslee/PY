package com.inforstack.openstack.period;

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
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.MapUtil;

@Service
@Transactional
public class PeriodServiceImpl implements PeriodService {
	
	private static final Log log = LogFactory.getLog(PeriodServiceImpl.class);
	@Autowired
	private PeriodDao periodDao;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private I18nService i18nService;
	
	@Override
	public Period createPeriod(Period period) {
		if(period == null){
			log.info("Create period failed for passed period is null");
			return null;
		}
		
		log.debug("Create period");
		periodDao.persist(period);
		log.debug("Create successfully");
		
		return period;
	}

	@Override
	public Period createPeriod(Map<Integer, String> nameMap, Integer type,
			Integer quantity) {
		if(MapUtil.isNullOrEmpty(nameMap) || type==null || quantity==null){
			log.info("Create period failed for passed nameMap/type/quantity is null or empty");
			return null;
		}
		
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
		
		Period period  = new Period();
		period.setDeleted(false);
		period.setName(i18n.get(0).getI18nLink());
		period.setQuantity(quantity);
		period.setType(type);
		periodDao.persist(period);
		
		log.debug("Create period successfully");
		return period;
	}

	@Override
	public Period deletePeriod(Period period) {
		if(period == null){
			log.info("Delete period failed for passed period is null");
			return null;
		}
		
		log.debug("Delete period with id : " + period.getId());
		period.setDeleted(true);
		log.debug("Delete period successfully");
		
		return period;
	}

	@Override
	public Period deletePeriod(Integer periodId) {
		if(periodId == null){
			log.info("Delete period failed for passed period id is null");
			return null;
		}
		
		log.debug("Delete period with id : " + periodId);
		Period period = periodDao.findById(periodId);
		if(period == null){
			log.info("Delete period failed for no period found by id : " + periodId);
			return null;
		}
		period.setDeleted(true);
		log.debug("Delete period successfully");
		
		return period;
	}

	@Override
	public List<Period> listAll(boolean includeDeleted) {
		log.debug("List all period(s) " + (includeDeleted?" include deleted " : " exclude deleted") );
		
		List<Period> pLst = periodDao.findAll(includeDeleted);
		if(CollectionUtil.isNullOrEmpty(pLst)){
			log.debug("No record found");
			return new ArrayList<Period>();
		}else{
			log.debug("List all successfully");
			return pLst;
		}
	}

	@Override
	public Period findPeriodById(Integer periodId) {
		if(periodId == null){
			log.info("Find period by id failed for passed id is null");
			return null;
		}
		
		log.info("Find period by id : " + periodId);
		Period period = periodDao.findById(periodId);
		if(period == null){
			log.debug("Find period by id failed for no instance found");
		}else{
			log.debug("Find period successfully");
		}
		
		return period;
	}

}
