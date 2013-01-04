package com.inforstack.openstack.i18n.lang;


public interface LanguageService {

	public Language findById(String languageId);

	public Language findByCountryAndLanguage(String country, String language);

}
