package edu.sibinfo.spring.web.module05.service.impl;

import edu.sibinfo.spring.web.module05.dao.RoleRepository;
import edu.sibinfo.spring.web.module05.dao.UserRepository;
import edu.sibinfo.spring.web.module05.domain.User;
import edu.sibinfo.spring.web.module05.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public void save(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles(new HashSet<>(roleRepository.findAll()));
    userRepository.save(user);
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public void autologin(String username, String password) {
    UserDetails details = userDetailsService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(details, password, details.getAuthorities());

    authenticationManager.authenticate(token);

    if (token.isAuthenticated()) {
      SecurityContextHolder.getContext().setAuthentication(token);
      logger.debug(String.format("Autologin %s successfully!", username));
    }
  }
}