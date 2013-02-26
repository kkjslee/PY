package com.inforstack.openstack.i18n.lang;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.NumberUtil;
import com.inforstack.openstack.utils.StringUtil;

@Service("languageService")
@Transactional
public class LanguageServiceImpl implements LanguageService {

  private static final Logger log = new Logger(LanguageServiceImpl.class);
  @Autowired
  private LanguageDao languageDao;

  @Override
  public Language findById(String languageId) {
    log.debug("Find Language by id" + languageId);

    Integer id = NumberUtil.getInteger(languageId);
    if (id == null) {
      log.info("Find Language failed for passed languageId is not a valid integer : " + languageId);
      return null;
    }
    Language lang = languageDao.findById(id);
    if (lang == null) {
      log.info("No language found for id : " + languageId);
    } else {
      log.debug("Find successfully");
    }

    return lang;
  }

  @Override
  public Language findByCountryAndLanguage(String country, String language) {
    log.info("Find language by country : " + country + ", language : " + language);
    Language lang = null;
    if (StringUtil.isNullOrEmpty(country)) {
      lang = languageDao.find(country, language);
      if (lang == null)
        lang = languageDao.find(language);
    } else {
      lang = languageDao.find(language);
    }

    if (lang == null) {
      log.info("No language found for country : " + country + ", language : " + language);
    } else {
      log.debug("Find language successfully");
    }

    return lang;
  }

  @Override
  public Language getDefaultLanguage() {
    log.debug("Get the default language");
    Language lang = languageDao.getDefault();
    if (lang == null) {
      log.error("No default language found");
    } else {
      log.debug("Get default language successfully");
    }

    return lang;
  }

  @Override
  public List<Language> list() {
    log.debug("Get all Languages");
    return languageDao.listAll();
  }
}
