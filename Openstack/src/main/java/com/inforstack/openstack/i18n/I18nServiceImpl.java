package com.inforstack.openstack.i18n;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class I18nServiceImpl implements I18nService {

	@Autowired
	private I18nDao i18nDao;

	@Override
	public I18n findByLinkAndLanguage(Integer linkId, Integer languageId) {
		return i18nDao.find(linkId, languageId);
	}

	@Override
	public I18n getFirstByLink(Integer linkId) {
		return i18nDao.findFirst(linkId);
	}

}
