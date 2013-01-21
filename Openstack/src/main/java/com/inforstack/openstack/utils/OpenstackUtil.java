package com.inforstack.openstack.utils;

import java.util.Locale;

import org.springframework.context.ApplicationContext;

import com.inforstack.openstack.api.keystone.KeystoneService.Role;
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
	
	public static String getMessage(String key){
		return context.getMessage(key, null, getLocale());
	}
	
	/**
	 * 
	 * @param roleId Constants.ROLE_ADMIN，, Constants.ROLE_USER, Constants.ROLE_AGENT
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Role getOpenstackRole(int roleId){
		if(roleId == Constants.ROLE_ADMIN){
			return Role.ADMIN;
		}else if(roleId == Constants.ROLE_USER){
			return Role.MEMBER;
		}else if(roleId == Constants.ROLE_AGENT){
			return Role.RESELL;
		}else{
			throw new IllegalArgumentException("Passed roleId is not valid : " + roleId);
		}
	}
}
