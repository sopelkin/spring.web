package edu.sibinfo.spring.web.module05.service.impl;

import edu.sibinfo.spring.web.module05.dao.UserRepository;
import edu.sibinfo.spring.web.module05.domain.Privilege;
import edu.sibinfo.spring.web.module05.domain.Role;
import edu.sibinfo.spring.web.module05.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user;
    try {
      user = userRepository.findByUsername(username);
    } catch (Exception e) {
      throw new UsernameNotFoundException("Not found");
    }
    if (user == null) {
      throw new UsernameNotFoundException("Not found");
    }

    return org.springframework.security.core.userdetails.User
        .withUsername(user.getUsername())
        .password(user.getPassword())
        .authorities(
            Stream.concat(
                user.getRoles()
                    .stream()
                    .map(Role::getName)
                    .map(SimpleGrantedAuthority::new),
                user.getRoles()
                    .stream()
                    .flatMap(role -> role.getPrivileges().stream())
                    .map(Privilege::getName)
                    .map(SimpleGrantedAuthority::new)
            ).collect(Collectors.toList()))
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
  }
}