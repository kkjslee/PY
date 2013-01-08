package com.inforstack.openstack.i18n;


public interface I18nDao {

	/**
	 * find i18n object with the given i18n link id and language id
	 * @param linkId
	 * @param languageId
	 * @return
	 */
	public I18n find(Integer linkId, Integer languageId);

	/**
	 * find the first i18n object ordered by language id by link id
	 * @param linkId
	 * @return
	 */
	public I18n findFirst(Integer linkId);

}
