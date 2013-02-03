package com.inforstack.openstack.i18n.lang;

import java.util.List;

public interface LanguageService {

  /**
   * find language by id
   * 
   * @param languageId
   * @return
   */
  public Language findById(String languageId);

  /**
   * Find language instance by country and language. If not found, find language instance by
   * language
   * 
   * @param country
   *          Locale country
   * @param language
   *          Locale language
   * @return
   */
  public Language findByCountryAndLanguage(String country, String language);

  /**
   * get the default language that is the first language instance in language table ordered by id
   * 
   * @return
   */
  public Language getDefaultLanguage();

  public List<Language> list();

}
