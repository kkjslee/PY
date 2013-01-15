package com.inforstack.openstack.i18n.link;

public interface I18nLinkService {
	
	public I18nLink createI18nLink(I18nLink link);
	
	/**
	 * 
	 * @param linkId
	 * @return the removed i18n link or null for fail
	 */
	public I18nLink removeI18nLink(Integer linkId);
}
