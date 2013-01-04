package com.inforstack.openstack.security.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import com.inforstack.openstack.security.role.Role;
import com.inforstack.openstack.security.role.RoleService;
import com.inforstack.openstack.utils.SecurityUtils;
import com.inforstack.openstack.utils.StringUtil;

@Service("accessDecisionManager")
public class OpenstackAccessDecisionManager implements AccessDecisionManager {
	
	private static final Log log = LogFactory.getLog(OpenstackAccessDecisionManager.class);

	@Autowired
	private RoleService roleService;
	
	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		log.debug("start Method - decide");
		if (configAttributes == null) {
			return;
		}
		
		String url = ((FilterInvocation)object).getRequestUrl();
		Role role = roleService.findRoleById(SecurityUtils.getUserRole());
		if(role==null || StringUtil.isNullOrEmpty(role.getName(), false) || !url.startsWith("/"+role.getName())){
			 log.error("Access denied : no role found or role not matched for user : " + SecurityUtils.getUserName());
		     throw new AccessDeniedException("No permission to access"); 
		}
		
        for(ConfigAttribute ca : configAttributes) {  
            String needPermission = ca.getAttribute();  
            if(SecurityUtils.AUTHENTICATED_USER_ROLE.equals(needPermission)){
            	log.debug("Role - 'AUTHENTICATED' needed" );
            	if(SecurityUtils.getUserName()!=null){
            		return;
            	}
            }else{
            	 for(GrantedAuthority ga : authentication.getAuthorities()) {
            		 if(ga==null) continue;
            		 
            		 List<String> authorities = getAllAuthority(ga.getAuthority());
            		 for(String authority : authorities){
            			 if(authority.equals(needPermission)) {  
                           	log.debug("Matched Authority found : " + needPermission);
                           	return;  
                         }
            		 }
                 }
            }
        } 
        log.error("Access denied : no matched Authority found. ");
        throw new AccessDeniedException("No permission to access");  
	}
	
	private List<String> getAllAuthority(String authority){
		List<String> authorities = new ArrayList<String>();
		if(!StringUtil.isNullOrEmpty(authority)){
			authorities.add(authority);
			
			int index = -1;
			while((index = authority.lastIndexOf("_")) != -1){
				authority = authority.substring(0, index);
				authorities.add(authority+"_admin");
			}
		}
		
		return authorities;
	}

	@Override
	public boolean supports(ConfigAttribute arg0) {
		return true;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
