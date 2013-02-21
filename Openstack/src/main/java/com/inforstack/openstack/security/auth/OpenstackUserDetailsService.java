package com.inforstack.openstack.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;
import com.inforstack.openstack.utils.OpenstackUtil;

@Service("userDetailsService")
public class OpenstackUserDetailsService implements UserDetailsService {

	private static final Logger log = new Logger(
			OpenstackUserDetailsService.class);
	@Autowired
	private UserService userService;

	private OpenstackUserDetails userDetails;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		log.debug("Invoke Method - loadUserByUsername");
		User user = userService.findByName(userName);
		if (user == null) {
			log.error("No user found");
			throw new UsernameNotFoundException("No user found for user : "
					+ userName);
		}
		log.debug("User found by name : " + userName);
		userDetails = (OpenstackUserDetails) OpenstackUtil
				.getBean("openstackUserDetails");
		userDetails.setUser(user);
		Tenant tenant = user.getDefaultTenant();
		tenant.getId();
		userDetails.setTenant(tenant);
		return userDetails;
	}

}
