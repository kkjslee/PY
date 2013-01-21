package com.inforstack.openstack.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.inforstack.openstack.security.auth.OpenstackUserDetails;
import com.inforstack.openstack.security.role.Role;
import com.inforstack.openstack.security.role.RoleService;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.user.User;

public class SecurityUtils {

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
			Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(o instanceof OpenstackUserDetails){
				OpenstackUserDetails ud = ((OpenstackUserDetails)o);
				Role role = ud.getRole();
				if(role == null){
					RoleService rs = (RoleService)OpenstackUtil.getBean("roleService");
					role = rs.findRoleById(ud.getUser().getRoleId());
					ud.setRole(role);
				}
				return role;
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

	
	public static User getUser(){
		try{
			Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(o instanceof OpenstackUserDetails){
				return ((OpenstackUserDetails)o).getUser();
			}
		}catch(Exception e){
		}
		
		return null;
	}
	
	public static Tenant getAgent(){
		try{
			Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(o instanceof OpenstackUserDetails){
				OpenstackUserDetails ud = ((OpenstackUserDetails)o);
				Tenant agent = ud.getAgent();
				if(agent == null){
					TenantService as = (TenantService)OpenstackUtil.getBean("tenantService");
					agent = as.findAgent(getTenant().getAgentId());
					ud.setAgent(agent);
				}
				return agent;
			}
		}catch(Exception e){
		}
		
		return null;
	}
	
	public static Tenant getTenant(){
		try{
			Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(o instanceof OpenstackUserDetails){
				OpenstackUserDetails ud = ((OpenstackUserDetails)o);
				Tenant tenant = ud.getTenant();
				if(tenant == null){
					TenantService ts = (TenantService)OpenstackUtil.getBean("tenantService");
					tenant = ts.findTenantById(ud.getUser().getDefaultTenantId());
					ud.setTenant(tenant);
				}
				return tenant;
			}
		}catch(Exception e){
		}
		
		return null;
	}
	
	public static boolean isUserEnabled(User user){
		if(user==null) return false;
		
		return user.getStatus() == Constants.USER_STATUS_VALID
				&& user.getAgeing() == Constants.USER_AGEING_ACTIVE;
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
