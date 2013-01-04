package com.inforstack.openstack.i18n;


public interface I18nService {

	public I18n findByLinkAndLanguage(Integer linkId, Integer languageId);

	public I18n getFirstByLink(Integer linkId);

}
