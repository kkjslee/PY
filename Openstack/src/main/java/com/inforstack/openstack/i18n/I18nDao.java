package com.inforstack.openstack.i18n;

import java.util.List;

import com.inforstack.openstack.utils.db.IDao;


public interface I18nDao extends IDao<I18n> {

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

	/**
	 * 
	 * @param i18nLinkId
	 * @return null if not found
	 */
	public List<I18n> findByLinkId(Integer i18nLinkId);
}
