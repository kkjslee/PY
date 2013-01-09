package com.inforstack.openstack.web.resolver;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import com.inforstack.openstack.i18n.lang.Language;
import com.inforstack.openstack.utils.OpenstackUtil;

public class CustomSessionLocaleResolver extends SessionLocaleResolver {
	
	public static final String LANGUAGE_SESSION_ATTRIBUTE_NAME = CustomSessionLocaleResolver.class.getName() + ".Language";
	
	public Language resolveLanguage(HttpServletRequest request){
		return (Language) WebUtils.getSessionAttribute(request, LANGUAGE_SESSION_ATTRIBUTE_NAME);
	}

	public void setLanguage(HttpServletRequest request,
			HttpServletResponse response, Language language) {
		Locale locale = OpenstackUtil.getLocale(language);
		super.setLocale(request, response, locale);
		WebUtils.setSessionAttribute(request, LANGUAGE_SESSION_ATTRIBUTE_NAME, language);
	}
	
}
