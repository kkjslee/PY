package com.inforstack.openstack.i18n;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.i18n.link.I18nLink;
import com.inforstack.openstack.i18n.link.I18nLinkService;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.StringUtil;


@Service("i18nService")
@Transactional
public class I18nServiceImpl implements I18nService {
	
	private static final Log log = LogFactory.getLog(I18nServiceImpl.class);
	@Autowired
	private I18nDao i18nDao;
	@Autowired
	private I18nLinkService i18nLinkService;

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
	public I18n createI18n(I18n i18n) {
		log.debug("Create i18n with link id : " + i18n.getI18nLinkId());
		i18nDao.persist(i18n);
		log.debug("Create i18n successfully");
		return i18n;
	}
	
	@Override
	public I18n createI18n(Integer languageId, String content, I18nLink i18nLink) {
		log.debug("Create i18n with language : " + languageId + ", content : "
				+ StringUtil.convertToLogString(content) + ", i18nLink : "
				+ i18nLink.getId());
		I18n i18n = new I18n();
		i18n.setContent(content);
		i18n.setI18nLink(i18nLink);
		i18n.setLanguageId(languageId);
		
		I18nService self = (I18nService)OpenstackUtil.getBean("i18nService");
		i18n = self.createI18n(i18n);
		
		if(i18n==null){
			log.debug("Create failed");
		}else{
			log.debug("Create successfully");
		}
		
		return i18n;
	}

	@Override
	public I18n createI18n(Integer languageId, String content,
			Integer i18nLinkId) {
		log.debug("Create i18n with language : " + languageId + ", content : "
				+ StringUtil.convertToLogString(content) + ", i18nlinkId : "
				+ i18nLinkId);
		I18nLink link = i18nLinkService.findI18nLink(i18nLinkId);
		if(link == null){
			log.info("Create i18n failed for i18n link not found by id : " + i18nLinkId);
			return null;
		}
		
		I18nService self = (I18nService)OpenstackUtil.getBean("i18nService");
		I18n i18n = self.createI18n(languageId, content, link);
		if(i18n==null){
			log.debug("Create failed");
		}else{
			log.debug("Create successfully");
		}
		
		return i18n;
	}
	
	@Override
	public I18n createI18n(Integer languageId, String content, String tableName, String columnName){
		log.info("Create i18n with language : " + languageId + ", content : " + StringUtil.convertToLogString(content) +
				", tableName : " + tableName + ", columnName : " + columnName);
		I18nLink link = i18nLinkService.createI18nLink(tableName, columnName);
		if(link == null){
			log.info("Create i18n link failed");
			return null;
		}
		I18nService self = (I18nService)OpenstackUtil.getBean("i18nService");
		I18n i18n = self.createI18n(languageId, content, link);
		if(i18n == null){
			log.info("Create failed");
		}else{
			log.info("Create successfully");
		}
		
		return i18n;
	}
	
	@Override
	public List<I18n> createI18n(Map<Integer, String> contentMap, String tableName,
			String columnName) {
		log.debug("Create I18n with tableName : " + tableName + ", columnName : " + columnName);
		I18nService self = (I18nService)OpenstackUtil.getBean("i18nService");
		List<I18n> i18nLst = new ArrayList<I18n>();
		for(Map.Entry<Integer, String> entry : contentMap.entrySet()){
			Integer language = entry.getKey();
			String content = entry.getValue();
			I18n i18n = null;
			if(i18nLst.isEmpty()){
				i18n = self.createI18n(language, content, tableName, columnName);
			}else{
				I18nLink link = i18nLst.get(0).getI18nLink();
				i18n = self.createI18n(language, content, link);
			}
			
			if(i18n==null){
				throw new ApplicationRuntimeException(
						"Create i18n failed for language : " + language
								+ ", content : "
								+ StringUtil.convertToLogString(content));
			}
			i18nLst.add(i18n);
		}
		
		log.debug("Create successfully");
		return i18nLst;
	}
	
	@Override
	public I18n updateI18n(Integer i18nId, String content){
		log.debug("Update i18n with id : "+ i18nId);
		I18n i18n = i18nDao.findById(i18nId);
		
		if(i18n == null){
			log.info("No i18n found by id : " + i18nId);
			return null;
		}
		i18n.setContent(content);
		
		log.debug("Update i18n successfully");
		return i18n;
	}
	
	@Override
	public I18n updateI18n(Integer i18nLinkId, Integer languageId, String content){
		log.debug("Update i18n with link id : "+ i18nLinkId + ", language : " +languageId + ", content : " + StringUtil.convertToLogString(content) );
		I18n i18n = i18nDao.find(i18nLinkId, languageId);
		
		if(i18n == null){
			log.info("No i18n found by link id : " + i18nLinkId + " and language id : " + languageId);
			return null;
		}
		i18n.setContent(content==null?"":content);
		
		log.debug("Update i18n successfully");
		return i18n;
	}
	
	@Override
	public List<I18n> updateOrCreateI18n(I18nLink i18nLink, Map<Integer, String> contentMap){
		log.debug("Update i18n with link : " + i18nLink.getId());
		
		I18nService self = (I18nService)OpenstackUtil.getBean("i18nService");
		List<I18n> i18nLst = new ArrayList<I18n>();
		for(Map.Entry<Integer, String> entry : contentMap.entrySet()){
			Integer language = entry.getKey();
			String content = entry.getValue();
			I18n i18n = i18nDao.find(i18nLink.getId(), language);
			if(i18n == null){
				i18n = self.createI18n(language, content, i18nLink);
				if(i18n == null){
					throw new ApplicationRuntimeException(
							"Create i18n failed with language : " + language +
							", content : " + StringUtil.convertToLogString(content) + 
							", link : " + i18nLink.getId());
				}
			}
			
			i18n.setContent(content);
		}
		
		log.debug("Create or update i18n successfully");
		return i18nLst;
	}
	
	public List<I18n> updateOrCreateI18n(Integer i18nLinkId, Map<Integer, String> contentMap){
		log.debug("Update i18n with link : " + i18nLinkId);
		
		I18nLink link = i18nLinkService.findI18nLink(i18nLinkId);
		if(link == null){
			log.info("Update or create i18n failed for no link found for id : " + i18nLinkId);
			return null;
		}
		
		I18nService self = (I18nService)OpenstackUtil.getBean("i18nService");
		List<I18n> i18nLst = self.updateOrCreateI18n(link, contentMap);
		
		if(i18nLst==null || i18nLst.isEmpty()){
			log.debug("Create or update i18n failed");
		}else{
			log.debug("Create or update i18n successfully");
		}
		return i18nLst;
	}
	
	@Override
	public I18n removeI18nById(Integer i18nId){
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
