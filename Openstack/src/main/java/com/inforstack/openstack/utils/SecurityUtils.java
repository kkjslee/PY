package com.inforstack.openstack.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.inforstack.openstack.security.auth.OpenstackUserDetails;
import com.inforstack.openstack.security.role.Role;
import com.inforstack.openstack.security.role.RoleService;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;

public class SecurityUtils {
	
	public static Integer getUserId(){
		try{
			Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(o instanceof OpenstackUserDetails){
				return ((OpenstackUserDetails)o).getUser().getId();
			}
		}catch(Exception e){
		}
		
		return null;
	}

	public static String getUserName() {
		try{
			Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(o instanceof OpenstackUserDetails){
				return ((OpenstackUserDetails)o).getUsername();
			}
		}catch(Exception e){
		}
		
		return null;
	}
	
	public static User getUser(){
		try{
			Integer userId = getUserId();
			if(userId != null){
				UserService us = (UserService)OpenstackUtil.getBean("userService");
				return us.findUserById(userId);
			}
		}catch(Exception e){
		}
		
		return null;
	}
	
	public static Integer getUserRoleId(){
		try{
			Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(o instanceof OpenstackUserDetails){
				return ((OpenstackUserDetails)o).getUser().getRoleId();
			}
		}catch(Exception e){
		}
		
		return null;
	}
	
	public static Role getUserRole() {
		try{
			Integer roleId = getUserRoleId();
			if(roleId != null){
				RoleService rs = (RoleService)OpenstackUtil.getBean("roleService");
				return rs.findRoleById(roleId);
			}
		}catch(Exception e){
		}
		
		return null;
	}
	
	public static OpenstackUserDetails getPrincipal(){
		try{
			Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(o instanceof OpenstackUserDetails){
				return (OpenstackUserDetails)o;
			}
		}catch(Exception e){
		}
		
		return null;
	}
	
	public static Integer getAgentId(){
		try{
			Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(o instanceof OpenstackUserDetails){
				return ((OpenstackUserDetails)o).getTenant().getAgentId();
			}
		}catch(Exception e){
		}
		
		return null;
	}
	
	public static Tenant getAgent(){
		try{
			Integer agentId = getAgentId();
			if(agentId != null){
				TenantService ts = (TenantService)OpenstackUtil.getBean("tenantService");
				return ts.findTenantById(agentId);
			}
		}catch(Exception e){
		}
		
		return null;
	}
	
	public static Integer getTenantId(){
		try{
			Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(o instanceof OpenstackUserDetails){
				return ((OpenstackUserDetails)o).getTenant().getId();
			}
		}catch(Exception e){
		}
		
		return null;
	}
	
	public static Tenant getTenant(){
		try{
			Integer tenantId = getTenantId();
			if(tenantId != null){
				TenantService ts = (TenantService)OpenstackUtil.getBean("tenantService");
				return ts.findTenantById(tenantId);
			}
		}catch(Exception e){
		}
		
		return null;
	}
	
	public static boolean isUserEnabled(User user){
		if(user==null) return false;
		
		return Constants.USER_STATUS_VALID.equals(user.getStatus())
				&& Constants.USER_AGEING_ACTIVE.equals(user.getAgeing());
	}
	
	public static boolean isTenantEnable(Tenant tenant){
		if(tenant==null) return false;
		
		return Constants.TENANT_AGEING_ACTIVE.equals(tenant.getAgeing());
	}
	
	public static boolean isAdminTenant(Tenant tenant){
		if(tenant==null) return false;
		
		if(Constants.ROLE_ADMIN == tenant.getRoleId()){
			return true;
		}else{
			return false;
		}
	}
}
