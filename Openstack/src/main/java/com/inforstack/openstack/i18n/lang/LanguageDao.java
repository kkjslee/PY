package com.inforstack.openstack.i18n.lang;

import com.inforstack.openstack.utils.db.IDao;


public interface LanguageDao extends IDao<Language> {

	public Language find(String country, String language);

	public Language find(String language);

	public Language getDefault();

}
