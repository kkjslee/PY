package com.inforstack.openstack.i18n;

import java.util.List;


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

	public void persist(I18n i18n);

	public I18n findI18nByLanguageAndId(Integer i18nId, Integer languageId);

	public void remove(I18n i18n);

	public I18n findById(Integer i18nId);

	/**
	 * 
	 * @param i18nLinkId
	 * @return null if not found
	 */
	public List<I18n> findByLinkId(Integer i18nLinkId);
}
