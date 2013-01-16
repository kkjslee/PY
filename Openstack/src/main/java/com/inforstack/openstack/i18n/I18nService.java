package com.inforstack.openstack.i18n;

import java.util.List;

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
	
	public I18n createI18n(Integer languageId, String content, I18nLink i18nLink);
	
	public I18n createI18n(Integer languageId, String content, Integer i18nLinkId);

	/**
	 * find i18n by id and language, then update the content
	 * @param i18nId
	 * @param languageId
	 * @param content
	 * @return
	 */
	public I18n updateI18n(Integer i18nId, Integer languageId, String content);

	/**
	 * 
	 * @param i18nId
	 * @return The i18n removed or null for fail
	 */
	public I18n removeI18nById(Integer i18nId);
	
	/**
	 * 
	 * @param i18nLinkId
	 * @return The i18n list removed or null ofr fail
	 */
	public List<I18n> removeI18nByLinkId(Integer i18nLinkId);
}
