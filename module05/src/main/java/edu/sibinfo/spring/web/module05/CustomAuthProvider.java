package edu.sibinfo.spring.web.module05;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomAuthProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();
    UserDetails user = buildUserDetails(username, password);
    if ("externaluser".equals(username) && "pass".equals(password)) {
      return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    } else {
      throw new BadCredentialsException("Custom auth failed");
    }
  }

  private UserDetails buildUserDetails(String username, String password) {
    return User.withUsername(username).password(password).roles("USER").build();
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
