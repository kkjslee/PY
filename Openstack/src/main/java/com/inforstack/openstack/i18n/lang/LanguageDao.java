package com.inforstack.openstack.i18n.lang;

import com.inforstack.openstack.basic.BasicDao;


public interface LanguageDao extends BasicDao<Language> {

	public Language find(String country, String language);

	public Language find(String language);

	public Language getDefault();

}
