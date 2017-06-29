package edu.sibinfo.spring.web.module05.service.impl;

import edu.sibinfo.spring.web.module05.dao.PasswordResetTokenRepository;
import edu.sibinfo.spring.web.module05.dao.RoleRepository;
import edu.sibinfo.spring.web.module05.dao.UserRepository;
import edu.sibinfo.spring.web.module05.domain.PasswordResetToken;
import edu.sibinfo.spring.web.module05.domain.User;
import edu.sibinfo.spring.web.module05.dto.UserDTO;
import edu.sibinfo.spring.web.module05.exception.UserAlreadyExistException;
import edu.sibinfo.spring.web.module05.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;
  private final AuthenticationManager authenticationManager;
  private final ConversionService conversionService;
  private final PasswordResetTokenRepository tokenRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, AuthenticationManager authenticationManager, ConversionService conversionService, PasswordResetTokenRepository tokenRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
    this.authenticationManager = authenticationManager;
    this.conversionService = conversionService;
    this.tokenRepository = tokenRepository;
  }

  @Override
  public void registerAccount(UserDTO userDTO) throws UserAlreadyExistException {
    if (accountExist(userDTO.getUsername())) {
      throw new UserAlreadyExistException("There is an account with username: " + userDTO.getUsername());
    }
    User user = conversionService.convert(userDTO, User.class);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles(new HashSet<>(roleRepository.findAll()));
    userRepository.save(user);
  }

  private boolean accountExist(String username) {
    return userRepository.findByUsername(username) != null;
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

  @Override
  public PasswordResetToken createPasswordResetToken(User user) {
    return tokenRepository.save(new PasswordResetToken(UUID.randomUUID().toString(), user));
  }

  @Override
  public User findUserByResetToken(String token) {
    PasswordResetToken passwordResetToken = tokenRepository.findByToken(token);
    User user = null;
    if (passwordResetToken != null) {
      user = passwordResetToken.getUser();
      tokenRepository.delete(passwordResetToken);
    }
    return user;
  }

  @Override
  @Transactional
  public void changeCurrentUserPassword(String password) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.isAuthenticated()) {
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      User user = userRepository.findByUsername(userDetails.getUsername());
      user.setPassword(passwordEncoder.encode(password));
      userRepository.save(user);
    }
  }
}