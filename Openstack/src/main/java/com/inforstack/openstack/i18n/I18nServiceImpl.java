package com.inforstack.openstack.i18n;


import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.i18n.link.I18nLink;
import com.inforstack.openstack.utils.OpenstackUtil;


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
	
	@Override
	public String getI18nContent(I18nLink i18nLink){
		if(i18nLink == null || i18nLink.getId()==null){
			log.info("Get i18n content failed for passed i18n link is null or link id is null");
			return null;
		}
		
		log.debug("Find i18n contennt by link id : " + i18nLink.getId());
		I18nService self = (I18nService)OpenstackUtil.getBean("i18nService");
		I18n i18n = self.findByLinkAndLanguage(i18nLink.getId(), OpenstackUtil.getLanguage().getId());
		if(i18n==null){
			i18n = self.getFirstByLink(i18nLink.getId());
		}
		
		if(i18n == null){
			log.info("No i18n content found by i18n link with id : " + i18nLink.getId());
		}else{
			log.debug("Get successfully");
		}
		
		return i18n==null ? null : i18n.getContent();
	}

	@Override
	public I18n addI18n(I18n i18n) {
		if(i18n == null || i18n.getI18nLink()==null){
			log.info("Add i18n failed for passed i18n is null or i18n link is null");
		}
		
		log.debug("Add new i18n with link id : " + i18n.getI18nLinkId());
		i18nDao.persist(i18n);
		log.debug("create user successfully");
		return i18n;
	}
	
	@Override
	public I18n updateI18n(Integer i18nId, Integer languageId, String content){
		if(i18nId==null){
			log.info("Update i18n failed for passed i18n id is null");
		}
		
		log.debug("Update i18n with id : "+ i18nId);
		I18n i18n = i18nDao.findI18nByLanguageAndId(i18nId, languageId);
		
		if(i18n == null){
			log.info("No i18n found by id : " + i18nId + ", language id : " + languageId);
			return null;
		}
		i18n.setContent(content);
		
		log.debug("Update i18n successfully");
		return i18n;
	}
	
	@Override
	public I18n removeI18nById(Integer i18nId){
		if(i18nId==null){
			log.info("Remove i18n failed for passed i18n id is null");
			return null;
		}
		
		log.debug("Remove i18n by id : " + i18nId);
		I18n i18n = i18nDao.findById(i18nId);
		if(i18n == null){
			log.info("Remove i18n failed for no i18n found by id : " + i18nId);
			return null;
		}
		i18nDao.remove(i18n);
		log.debug("Remove successfully");
		return i18n;
	}

	@Override
	public List<I18n> removeI18nByLinkId(Integer i18nLinkId) {
		if(i18nLinkId == null){
			log.info("remove i18n failed for passed link id is null");
			return null;
		}
		
		log.debug("Remove i18n by link id : " + i18nLinkId);
		List<I18n> i18nLst = i18nDao.findByLinkId(i18nLinkId);
		if(i18nLst == null){
			log.info("Remove 18n failed for no i18n found by link id : " + i18nLinkId);
			return null;
		}
		
		for(I18n i18n : i18nLst){
			i18nDao.remove(i18n);
		}
		log.debug("Remove successfully");
		return i18nLst;
	}
}
