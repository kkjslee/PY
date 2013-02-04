package com.inforstack.openstack.security.auth;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;
import com.inforstack.openstack.utils.OpenstackUtil;


@Service("userDetailsService")
public class OpenstackUserDetailsService implements UserDetailsService {
	
	private static final Log log = LogFactory.getLog(OpenstackUserDetailsService.class);
	@Autowired
	private UserService userService;
	
	private OpenstackUserDetails userDetails;
	
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		log.debug("Invoke Method - loadUserByUsername");
		User user = userService.findByName(userName);
		if(user==null){
			log.error("No user found");
			throw new UsernameNotFoundException("No user found for user : " + userName );  
		}
		log.debug("User found by name : "+userName);
		userDetails = (OpenstackUserDetails)OpenstackUtil.getBean("openstackUserDetails");
		userDetails.setUser(user);  
		userDetails.setTenant(user.getDefaultTenant());
		return userDetails;
	}

}
