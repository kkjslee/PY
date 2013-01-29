package com.inforstack.openstack.i18n;

import java.util.List;
import java.util.Map;

import com.inforstack.openstack.i18n.link.I18nLink;


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

	/**
	 * find i18n content by the given i18nlink
	 * <li>It will try to get the contennt using the language binded with the user fisrt.</li>
	 * <li>If no content is found , then it will try to get the first it can find ordered by language</li>
	 * @param i18nLink
	 * @return
	 */
	public String getI18nContent(I18nLink i18nLink);

	/**
	 * create new i18n
	 * @param i18n
	 * @return
	 */
	public I18n createI18n(I18n i18n);
	
	/**
	 * create i18n with provided language, content and i18nLink
	 * @param languageId
	 * @param content
	 * @param i18nLink
	 * @return
	 */
	public I18n createI18n(Integer languageId, String content, I18nLink i18nLink);
	
	/**
	 * create i18n with provided language, content and i18nLink
	 * @param languageId
	 * @param content
	 * @param i18nLinkId
	 * @return
	 */
	public I18n createI18n(Integer languageId, String content, Integer i18nLinkId);
	
	/**
	 * create i18n and i18nLink with provided language, content, tableName and columnName
	 * @param languageId
	 * @param content
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public I18n createI18n(Integer languageId, String content, String tableName, String columnName);
	
	/**
	 * create i18n and i18nlink with provided contentMap, tableName and columnName
	 * @param contentMap key is language, value is related content
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public List<I18n> createI18n(Map<Integer, String> contentMap, String tableName, String columnName);

	/**
	 * find i18n by id and language, then update the content
	 * @param i18nId
	 * @param content
	 * @return
	 */
	public I18n updateI18n(Integer i18nId, String content);
	
	/**
	 * update the content of i18n by language and i18nlink to the given content
	 * @param i18nLinkId
	 * @param languageId
	 * @param content
	 * @return
	 */
	public I18n updateI18n(Integer i18nLinkId, Integer languageId, String content);
	
	/**
	 * update i18n content if exist or create a new one
	 * @param i18nLink
	 * @param contentMap
	 * @return
	 */
	public List<I18n> updateOrCreateI18n(I18nLink i18nLink, Map<Integer, String> contentMap);
	
	/**
	 * update i18n content if exist or create a new one
	 * @param i18nLinkId
	 * @param contentMap
	 * @return
	 */
	public List<I18n> updateOrCreateI18n(Integer i18nLinkId, Map<Integer, String> contentMap);

	/**
	 * 
	 * @param i18nId
	 * @return The i18n removed or null for fail
	 */
	public I18n removeI18nById(Integer i18nId);
	
	/**
	 * 
	 * @param i18nLinkId
	 * @return The i18n list removed or null for fail
	 */
	public List<I18n> removeI18nByLinkId(Integer i18nLinkId);

}
