package com.inforstack.openstack.i18n;


public interface I18nDao {

	public I18n find(Integer linkId, Integer languageId);

	public I18n findFirst(Integer linkId);

}
