package com.diesgut.medical.app.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diesgut.medical.app.dao.security.IUserDao;
import com.diesgut.medical.model.security.UserRole;

@Service
public class SecurityUserServiceImp implements UserDetailsService {

	@Autowired
	IUserDao iUserDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.diesgut.medical.model.security.User user = iUserDao.findByUsername(username);
		List<GrantedAuthority> authorities = this.buildAuthorities(user.getUserRoles());
		return buildUser(user, authorities);
	}

	private User buildUser(com.diesgut.medical.model.security.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildAuthorities(List<UserRole> userRoles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (UserRole uRole : userRoles) {
			String complex = "ROLE_" + uRole.getRole();
			authorities.add(new SimpleGrantedAuthority(complex));
		}
		return authorities;
	}

	@Transactional
	public void saveUser(com.diesgut.medical.model.security.User user) {
		iUserDao.save(user);
	}

}
