package com.inforstack.openstack.web.interceptor;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.inforstack.openstack.i18n.lang.Language;
import com.inforstack.openstack.i18n.lang.LanguageService;
import com.inforstack.openstack.i18n.model.I18nContext;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.web.resolver.CustomSessionLocaleResolver;

/**
 * Interceptor that allows for changing the current locale on every request,
 * via a configurable request parameter.
 *
 * @author Juergen Hoeller
 * @since 20.06.2003
 * @see org.springframework.web.servlet.LocaleResolver
 */
public class CustomLocaleChangeInterceptor extends HandlerInterceptorAdapter {

	/**
	 * Default name of the locale specification parameter: "locale".
	 */
	public static String DEFAULT_PARAM_NAME = "locale";

	private String paramName = DEFAULT_PARAM_NAME;
	private LanguageService languageService;

	/**
	 * Set the name of the parameter that contains a locale specification
	 * in a locale change request. Default is "locale".
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * Return the name of the parameter that contains a locale specification
	 * in a locale change request.
	 */
	public String getParamName() {
		return this.paramName;
	}


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {

		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		if (localeResolver == null) {
			throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
		}
		
		String languageCode = request.getParameter(this.paramName);
		if (languageCode != null) {
			Language language = languageService.findById(languageCode);
			if(localeResolver instanceof CustomSessionLocaleResolver){
				((CustomSessionLocaleResolver)localeResolver).setLanguage(request, response, language);
			}else{
				Locale locale = OpenstackUtil.getLocale(language);
				localeResolver.setLocale(request, response, locale);
			}
		}
		
		Locale locale = localeResolver.resolveLocale(request);
		Language language = null;
		if(localeResolver instanceof CustomSessionLocaleResolver){
			language = ((CustomSessionLocaleResolver)localeResolver).resolveLanguage(request);
		}
		
		if(language == null){
			language = languageService.findByCountryAndLanguage(locale.getCountry(), locale.getLanguage());
		}
		if(language == null){
			language = languageService.getDefaultLanguage();
			locale = OpenstackUtil.getLocale(language);
			localeResolver.setLocale(request, response, locale);
		}
		OpenstackUtil.setI18nResolver(new I18nContext(locale, language));
		
		// Proceed in any case.
		return true;
	}

	public LanguageService getLanguageService() {
		return languageService;
	}

	public void setLanguageService(LanguageService languageService) {
		this.languageService = languageService;
	}

	
}
