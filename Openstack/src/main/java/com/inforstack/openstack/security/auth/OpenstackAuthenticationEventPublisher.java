package com.inforstack.openstack.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;

@Service("authenticationEventPublisher")
public class OpenstackAuthenticationEventPublisher extends
		DefaultAuthenticationEventPublisher {
	
	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public void publishAuthenticationSuccess(Authentication authentication) {
		String username = authentication.getName();
		User user = userService.findByName(username);
		user.setTried(0);
		super.publishAuthenticationSuccess(authentication);
	}

	@Override
	@Transactional
	public void publishAuthenticationFailure(AuthenticationException exception,
			Authentication authentication) {
		String username = authentication.getName();
		User user = userService.findByName(username);
		if(user.getTried() == null){
			user.setTried(0);
		}
		user.setTried(user.getTried() + 1);
		super.publishAuthenticationFailure(exception, authentication);
	}

	
	
}
