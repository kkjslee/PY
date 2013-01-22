package com.inforstack.openstack.i18n.dict;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.StringUtil;

@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService {
	
	private static final Log log = LogFactory.getLog(DictionaryServiceImpl.class);
	@Autowired
	private DictionaryDao dictionaryDao;
	
	@Override
	public List<Dictionary> findDictsByKeyAndLanguageId(String key, Integer languageId) {
		List<Dictionary> dicts = new ArrayList<Dictionary>();
		if(StringUtil.isNullOrEmpty(key) || languageId == null){
			log.debug("Find dictionarys failed for passed key/languageId is null or empty");
			return dicts;
		}
		
		log.debug("Find dictionarys by key : " + key + ", language : " + languageId);
		dicts = dictionaryDao.findByKeyAndLanguage(key, languageId);
		if(dicts == null){
			log.info("No dictionary found by key : " + key + ", language : " + languageId);
		}else{
			log.debug("Find successfully");
		}
		
		return dicts;
	}

	@Override
	public Dictionary findDict(String key, Integer languageId, String code) {
		if(StringUtil.isNullOrEmpty(key) || languageId == null || StringUtil.isNullOrEmpty(code)){
			log.debug("Find dictionary failed for passed key/languageId/code is null or empty");
			return null;
		}
		
		log.debug("Find dictionarys by key : " + key + ", language : " + languageId + ", code : " + code);
		Dictionary dict = dictionaryDao.findDictionary(key, languageId, code);
		if(dict == null){
			log.info("No dictionary found by key : " + key + ", language : " + languageId + ", code : " + code);
		}else{
			log.debug("Find successfully");
		}
		
		return dict;
	}

	@Override
	public boolean contains(String key, String code) {
		if(StringUtil.isNullOrEmpty(key) || StringUtil.isNullOrEmpty(code)){
			log.debug("Find dictionary failed for passed key/code is null or empty");
			return false;
		}
		
		log.debug("Find dictionarys by key : " + key + ", code : " + code);
		List<Dictionary> dicts = dictionaryDao.findDictionary(key, code);
		if(CollectionUtil.isNullOrEmpty(dicts)){
			log.info("No dictionary found by key : " + key + ", code : " + code);
			return false;
		}else{
			log.debug("Find successfully");
			return true;
		}
	
	}


}
