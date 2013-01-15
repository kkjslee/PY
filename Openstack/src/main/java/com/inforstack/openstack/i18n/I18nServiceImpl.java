package com.inforstack.openstack.i18n;


import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("i18nService")
@Transactional
public class I18nServiceImpl implements I18nService {
	
	private static final Log log = LogFactory.getLog(I18nServiceImpl.class);
	@Autowired
	private I18nDao i18nDao;

	@Override
	public I18n findByLinkAndLanguage(Integer linkId, Integer languageId) {
		log.debug("find i18n instance by i18nlink id : "+linkId+", language id : "+languageId);
		I18n i18n = i18nDao.find(linkId, languageId);
		if(i18n == null){
			log.info("No i18n instance found");
		}else{
			log.debug("I18n instance found with content : "+i18n.getContent());
		}
		
		return i18n;
	}

	@Override
	public I18n getFirstByLink(Integer linkId) {
		log.debug("find the first I18n instance ordered by language id with i18n link id : "+linkId);
		I18n i18n = i18nDao.findFirst(linkId);
		
		if(i18n==null){
			log.info("No i18n instance found for link id : " + linkId);
		}else{
			log.debug("I18n instance found with content : "+i18n.getContent());
		}
		
		return i18n;
	}

}
