package com.inforstack.openstack.i18n.link;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.i18n.I18nDao;

@Service
@Transactional
public class I18nLinkServiceImpl implements I18nLinkService{
	
	private static final Log log = LogFactory.getLog(I18nLinkServiceImpl.class);
	@Autowired
	private I18nLinkDao i18nLinkDao;

	@Override
	public I18nLink createI18nLink(I18nLink link) {
		if(link == null){
			log.info("Create i18n link failed for passed i18nlink is null");
		}
		
		log.debug("Create i18n link with tableName : " + link.getTableName() + ", colomnName : " +link.getColumnName());
		i18nLinkDao.persist(link);
		log.debug("Create successfully");
		return link;
	}

	@Override
	public I18nLink removeI18nLink(Integer linkId) {
		if(linkId == null){
			log.info("Delete i18n link failed for passed i18nlink id is null");
			return null;
		}
		
		log.debug("Delete i18n link by with id : " + linkId);
		I18nLink link = i18nLinkDao.findById(linkId);
		if(link == null){
			log.info("Remove i18n link failed for no instance found by id : " + linkId);
			return null;
		}
		i18nLinkDao.remove(link);
		
		return link;
	}

}
