package com.inforstack.openstack.security.auth;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.inforstack.openstack.security.permission.Permission;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;


@Component
public class OpenstackUserDetails implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -423843895615465679L;
	private static final Log log = LogFactory.getLog(OpenstackUserDetails.class);
	private User user;
	@Autowired
	private UserService userService;
	
	public OpenstackUserDetails(){
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		log.debug("Invoke Method - getAuthorities");
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>(); 
		
		List<Permission> permissions = userService.getPermissions(user.getId());
		for(Permission p : permissions){
			grantedAuthorities.add(new SimpleGrantedAuthority(p.getName()));
		}
		log.debug(grantedAuthorities.size() + " GrantedAuthority found");
        return grantedAuthorities;  
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isValid() && user.isDeleted();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
