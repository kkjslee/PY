package com.inforstack.openstack.i18n.lang;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.utils.NumberUtil;

@Service("languageService")
@Transactional
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	private LanguageDao languageDao;

	@Override
	public Language findById(String languageId) {
		Integer id = NumberUtil.getInteger(languageId);
		return id==null? null : languageDao.findById(id);
	}

	@Override
	public Language findByCountryAndLanguage(String country, String language) {
		return languageDao.find(country, language);
	}
}
