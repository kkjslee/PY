package com.inforstack.openstack.utils;


import org.springframework.security.core.context.SecurityContextHolder;

import com.inforstack.openstack.security.auth.OpenstackUserDetails;
import com.inforstack.openstack.security.role.Role;
import com.inforstack.openstack.security.role.RoleService;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.agent.Agent;
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
				User user = ((OpenstackUserDetails)o).getUser();
				Role role = user.getRole();
				if(role == null){
					RoleService rs = (RoleService)OpenstackUtil.getBean("roleService");
					role = rs.findRoleById(user.getRoleId());
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
	
	public static Agent getAgent(){
		try{
			Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(o instanceof OpenstackUserDetails){
				return ((OpenstackUserDetails)o).getAgent();
			}
		}catch(Exception e){
		}
		
		return null;
	}
	
	public static Tenant getTenant(){
		try{
			Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(o instanceof OpenstackUserDetails){
				return ((OpenstackUserDetails)o).getTenant();
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
