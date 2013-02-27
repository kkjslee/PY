package com.inforstack.openstack.utils;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.inforstack.openstack.api.keystone.KeystoneService.Role;
import com.inforstack.openstack.configuration.ConfigurationDao;
import com.inforstack.openstack.configuration.ConfigurationService;
import com.inforstack.openstack.controller.model.I18nModel;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.i18n.lang.Language;
import com.inforstack.openstack.i18n.model.I18nContext;
import com.inforstack.openstack.log.Logger;

public class OpenstackUtil {

	private static final Logger log = new Logger(OpenstackUtil.class);

	private static ApplicationContext context;
	private static final ThreadLocal<I18nContext> localeConext = new ThreadLocal<I18nContext>();
	private static ViewResolver viewResolver;
	private static ConfigurationService configurationService;

	public static Locale getLocale(Language language) {
		if (language == null) {
			return null;
		} else {
			return new Locale(language.getLanguage(), language.getCountry());
		}
	}

	public static void setI18nResolver(I18nContext context) {
		localeConext.set(context);
	}

	public static Locale getLocale() {
		return localeConext.get().getLocale();
	}

	public static Language getLanguage() {
		return localeConext.get().getLanguage();
	}

	public static String getContent(I18nModel[] i18n) {
		String content = "";
		Integer languageId = OpenstackUtil.getLanguage().getId();
		for (I18nModel model : i18n) {
			if (model.getLanguageId() == languageId) {
				content = model.getContent();
				break;
			}
		}
		return content;
	}

	public static void setContext(ApplicationContext context) {
		OpenstackUtil.context = context;
	}

	public static Object getBean(String name) {
		return context.getBean(name);
	}

	public static <T> T getBean(Class<T> clz) {
		return context.getBean(clz);
	}

	public static ViewResolver getViewResolver(HttpServletRequest req) {
		synchronized (OpenstackUtil.class) {
			if (viewResolver == null) {
				viewResolver = (ViewResolver) RequestContextUtils
						.getWebApplicationContext(req).getBean("viewResolver");
			}
		}

		return viewResolver;
	}

	public static String getMessage(String key, Object... args) {
		return context.getMessage(key, args, getLocale());
	}
	
	public static String getConfig(String name){
		synchronized (OpenstackUtil.class) {
			if(configurationService == null){
				configurationService = getBean(ConfigurationService.class);
			}
		}
		
		return configurationService.getValueByName(name);
	}

	/**
	 * 
	 * @param roleId
	 *            Constants.ROLE_ADMIN，, Constants.ROLE_USER,
	 *            Constants.ROLE_AGENT
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Role getOpenstackRole(int roleId) {
		if (roleId == Constants.ROLE_ADMIN) {
			return Role.ADMIN;
		} else if (roleId == Constants.ROLE_USER) {
			return Role.MEMBER;
		} else if (roleId == Constants.ROLE_AGENT) {
			return Role.RESELL;
		} else {
			throw new IllegalArgumentException("Passed roleId is not valid : "
					+ roleId);
		}
	}

	public static String md5(String s) {
		return new MessageDigestPasswordEncoder("md5").encodePassword(s, null);
	}

	public static Object getProperty(Object o, String prop) {
		if (prop == null || o == null)
			return null;
		try {
			int index = prop.indexOf(".");
			if (index == -1) {
				return o.getClass()
						.getMethod("get" + StringUtils.capitalize(prop))
						.invoke(o);
			} else {
				return getProperty(
						o.getClass()
								.getMethod(
										"get"
												+ StringUtils.capitalize(prop
														.substring(0, index)))
								.invoke(o), prop.substring(index + 1));
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static Map<String, Object> buildSuccessResponse(Object result) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put(Constants.AJAX_RESPONSE_KEY_STATUS,
				Constants.AJAX_RESPONSE_STATUS_SUCCESS);
		ret.put(Constants.AJAX_RESPONSE_KEY_RESULT, result);

		return ret;
	}

	public static Map<String, Object> buildErrorResponse(String errorMsg) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put(Constants.AJAX_RESPONSE_KEY_STATUS,
				Constants.AJAX_RESPONSE_STATUS_ERROR);
		ret.put(Constants.AJAX_RESPONSE_KEY_RESULT, errorMsg);

		return ret;
	}
	
	public static String buildSuccessJsonString(Object result){
		try {
			return new ObjectMapper().writeValueAsString(buildSuccessResponse(result));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ApplicationRuntimeException("Build success result String failed");
		}
	}
	
	public static String buildErrorsJsonString(String errorMsg){
		try {
			return new ObjectMapper().writeValueAsString(buildErrorResponse(errorMsg));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ApplicationRuntimeException("Build error result String failed");
		}
	}

	public static String getJspPage(String viewName, Map<String, ?> model,
			HttpServletRequest request, HttpServletResponse resp) {
		MockHttpServletResponse response = new MockHttpServletResponse();
		response.setCharacterEncoding(resp.getCharacterEncoding());
		try {
			getViewResolver(request).resolveViewName(viewName, getLocale())
					.render(model, request, response);
			return response.getContentAsString()
					.replaceAll("(^\\s+|\\s+$)", "");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public static boolean isValidProperty(String prop) {
		if (StringUtil.isNullOrEmpty(prop, false))
			return false;

		for (int i = 0, n = prop.length(); i < n; i++) {
			char c = prop.charAt(i);
			if (c != '.' && Character.isJavaIdentifierPart(c) == false) {
				return false;
			}
		}

		return true;
	}

	public static Object nulltoEmpty(Object o) {
		if (o == null)
			return "";
		return o;
	}
	
	public static void close(Closeable closeable){
		if(closeable != null){
			try {
				closeable.close();
			} catch (IOException e) {
				log.warn(e.getMessage(), e);
			}
		}
	}

	public static void close(Socket s) {
		if(s != null){
			try {
				s.close();
			} catch (IOException e) {
				log.warn(e.getMessage(), e);
			}
		}
	}
}
