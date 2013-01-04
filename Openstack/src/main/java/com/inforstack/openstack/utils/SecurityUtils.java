package com.inforstack.openstack.utils;


import org.springframework.security.core.context.SecurityContextHolder;

import com.inforstack.openstack.security.auth.OpenstackUserDetails;


public class SecurityUtils {

	public static final String AUTHENTICATED_USER_ROLE = "-1";

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
	
	public static Integer getUserRole() {
		try{
			Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(o instanceof OpenstackUserDetails){
				return ((OpenstackUserDetails)o).getUser().getRoleId();
			}
		}catch(Exception e){
		}
		
		return null;
	}

}
