package com.inforstack.openstack.utils;


import org.springframework.security.core.context.SecurityContextHolder;

import com.inforstack.openstack.security.auth.OpenstackUserDetails;
import com.inforstack.openstack.security.role.Role;


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
				return ((OpenstackUserDetails)o).getUser().getRole();
			}
		}catch(Exception e){
		}
		
		return null;
	}

}
