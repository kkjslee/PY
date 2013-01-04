package com.inforstack.openstack.i18n.model;

import java.util.Locale;

import com.inforstack.openstack.i18n.lang.Language;



public class I18nContext {
	
	private Locale locale;
	private Language language;

	public I18nContext(Locale locale, Language language){
		this.setLocale(locale);
		this.setLanguage(language);
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

}
