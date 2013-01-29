package com.inforstack.openstack.i18n.link;

public interface I18nLinkService {
	
	/**
	 * create i18nLink
	 * @param link
	 * @return
	 */
	public I18nLink createI18nLink(I18nLink link);
	
	/**
	 * create i18nLink with the given tableName and columnName
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public I18nLink createI18nLink(String tableName, String columnName);
	
	/**
	 * Remove i18nLink by id
	 * @param linkId
	 * @return the removed i18n link or null for fail
	 */
	public I18nLink removeI18nLink(Integer linkId);
	
	/**
	 * find i18nLink by id
	 * @param linkId
	 * @return
	 */
	public I18nLink findI18nLink(Integer linkId);
}
