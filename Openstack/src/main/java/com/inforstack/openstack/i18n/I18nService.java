package com.inforstack.openstack.i18n;


public interface I18nService {

	/**
	 * find i18n by i18n link id and language id
	 * @param linkId
	 * @param languageId
	 * @return
	 */
	public I18n findByLinkAndLanguage(Integer linkId, Integer languageId);
	
	/**
	 * find the first i18n ordered by language id
	 * @param linkId
	 * @return
	 */
	public I18n getFirstByLink(Integer linkId);

}
