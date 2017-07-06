package edu.sibinfo.spring.web.module05.service.impl;

import edu.sibinfo.spring.web.module05.dao.UserRepository;
import edu.sibinfo.spring.web.module05.domain.Privilege;
import edu.sibinfo.spring.web.module05.domain.Role;
import edu.sibinfo.spring.web.module05.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.security.sasl.AuthenticationException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User user;
	try {
	  user = userRepository.findByUsername(username);
    } catch (Exception e) {
    	throw new UsernameNotFoundException("Not found");
    }
    
    if (user == null) {
    	throw new UsernameNotFoundException(String.format("Username '%s' not found", username));
    }
    
    Collection<Privilege> privileges = user.getAuthorities();
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    for (Privilege privilege : privileges) {
      grantedAuthorities.add(new SimpleGrantedAuthority(privilege.getName()));
    }
    for (Role role : user.getRoles()) {
    	grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
    }
    
    return org.springframework.security.core.userdetails.User
    	.withUsername(user.getUsername())
    	.password(user.getPassword())
    	.authorities(new ArrayList<>(grantedAuthorities))
    	.build();
  }
}