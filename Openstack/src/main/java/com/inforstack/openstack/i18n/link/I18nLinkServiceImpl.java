package com.inforstack.openstack.i18n.link;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.StringUtil;

@Service("i18nLinkService")
@Transactional
public class I18nLinkServiceImpl implements I18nLinkService{
	
	private static final Logger log = new Logger(I18nLinkServiceImpl.class);
	@Autowired
	private I18nLinkDao i18nLinkDao;

	@Override
	public I18nLink createI18nLink(I18nLink link) {
		log.debug("Create i18n link with tableName : " + link.getTableName() + ", colomnName : " +link.getColumnName());
		i18nLinkDao.persist(link);
		log.debug("Create successfully");
		return link;
	}
	
	@Override
	public I18nLink createI18nLink(String tableName, String columnName) {
		log.debug("Create i18n link with tableName : " + tableName + ", columnName : " + columnName);
		I18nLinkService self = (I18nLinkService)OpenstackUtil.getBean("i18nLinkService");
		I18nLink link = new I18nLink();
		link.setColumnName(columnName);
		link.setTableName(tableName);
		
		link =  self.createI18nLink(link);
		if(link == null){
			log.debug("Create failed");
		}else{
			log.debug("Create successfully");
		}
		
		return link;
	}
	
	@Override
	public I18nLink removeI18nLink(Integer linkId) {
		log.debug("Delete i18n link by id : " + linkId);
		I18nLink link = i18nLinkDao.findById(linkId);
		if(link == null){
			log.info("Remove i18n link failed for no instance found by id : " + linkId);
			return null;
		}
		i18nLinkDao.remove(link);
		log.debug("Remove successfully");
		
		return link;
	}

	@Override
	public I18nLink findI18nLink(Integer linkId) {
		log.debug("Find i18n link by id : " + linkId);
		I18nLink link = i18nLinkDao.findById(linkId);
		if(link == null){
			log.info("Find failed");
		}else{
			log.debug("Find successfully");
		}
		
		return link;
	}

}
