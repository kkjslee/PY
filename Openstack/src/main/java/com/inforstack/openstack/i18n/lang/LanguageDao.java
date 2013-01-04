package com.inforstack.openstack.i18n.lang;


public interface LanguageDao {

	public Language findById(Integer languageId);

	public Language find(String country, String language);

	public Language find(String language);

	public Language getDefault();

}
