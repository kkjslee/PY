package com.inforstack.openstack.web.interceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.security.auth.OpenstackUserDetails;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.tenant.agent.Agent;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.NumberUtil;
import com.inforstack.openstack.utils.SecurityUtils;

public class CustomInterceptor extends HandlerInterceptorAdapter{
	
	private static final String DEFAULT_TENANT_PARAM_NAME = "tenant";
	private String tenantParam = DEFAULT_TENANT_PARAM_NAME;
	private TenantService tenantService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {
		handleTenant(request, response, handler);
		handleAgent(request, response, handler);
		
		return true;
	}

	private void handleAgent(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		OpenstackUserDetails principal = SecurityUtils.getPrincipal();
		if(principal != null){
			Agent agent = (Agent)WebUtils.getSessionAttribute(request, Constants.SESSION_ATTRIBUTE_NAME_AGENT);
			principal.setAgent(agent);
		}
	}

	public void handleTenant(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {
		
		OpenstackUserDetails principal = SecurityUtils.getPrincipal();
		if(principal != null){
			Tenant tenant = null;
			
			String tenantCode = request.getParameter(this.tenantParam);
			if(tenantCode != null){
				Integer tenantId = NumberUtil.getInteger(tenantCode);
				if(tenantId != null){
					tenant = tenantService.findTenantById(tenantId);
				}
			}
			if(tenant == null) {
				tenant = principal.getTenant();
				if(tenant == null){
					tenant = tenantService.findTenantById(principal.getUser().getDefaultTenantId());
				}
			}
			
			if(tenant == null){
				throw new ApplicationRuntimeException("No tenant found for user : " + principal.getUsername());
			}else{
				principal.setTenant(tenant);
			}
		}
		
	}
	
	public String getTenantParam() {
		return tenantParam;
	}

	public void setTenantParam(String tenantParam) {
		this.tenantParam = tenantParam;
	}

	public TenantService getTenantService() {
		return tenantService;
	}

	public void setTenantService(TenantService tenantService) {
		this.tenantService = tenantService;
	}
}
