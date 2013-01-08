package com.inforstack.openstack.web.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.inforstack.openstack.utils.OpenstackUtil;

public class OpenstackContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		OpenstackUtil.setContext(
				WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext())
		);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
	
}
