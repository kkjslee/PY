package com.inforstack.openstack.utils;

import java.util.Locale;

import org.springframework.context.ApplicationContext;

import com.inforstack.openstack.i18n.lang.Language;
import com.inforstack.openstack.i18n.model.I18nContext;

public class OpenstackUtil {
	
	private static ApplicationContext context;
	private static final ThreadLocal<I18nContext> localeConext = new ThreadLocal<I18nContext>();
	
	public static Locale getLocale(Language language){
		if(language==null){
			return null;
		}else{
			return new Locale(language.getLanguage(), language.getCountry());
		}
	}
	
	public static void setI18nResolver(I18nContext context){
		localeConext.set(context);
	}
	
	public static Locale getLocale(){
		return localeConext.get().getLocale();
	}
	
	public static Language getLanguage(){
		return localeConext.get().getLanguage();
	}

	public static Object getBean(String name) {
		return context.getBean(name);
	}

	public static void setContext(ApplicationContext context) {
		OpenstackUtil.context = context;
	}
}
