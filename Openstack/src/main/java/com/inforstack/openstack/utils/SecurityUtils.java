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
	
	public static Role getUserRole() {
		try{
			Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(o instanceof OpenstackUserDetails){
				RoleService rs = (RoleService)OpenstackUtil.getBean("roleService");
				return rs.findRoleById(((OpenstackUserDetails)o).getUser().getRoleId());
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
	
}
