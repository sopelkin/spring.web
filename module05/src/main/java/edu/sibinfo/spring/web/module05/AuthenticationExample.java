package edu.sibinfo.spring.web.module05;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationExample {
  private static AuthenticationManager am = new SampleAuthenticationManager();

  public static void main(String[] args) throws Exception {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    System.out.println("Please enter your password:");
    String password = in.readLine();
    PasswordEncoder enc = new BCryptPasswordEncoder();
    for(int i=0; i<5; i++) {
  	  System.out.println(enc.encode(password));
    }
  }
}

class SampleAuthenticationManager implements AuthenticationManager {
  private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();

  static {
    AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
  }

  public Authentication authenticate(Authentication auth) throws AuthenticationException {
    if (auth.getName().equals(auth.getCredentials())) {
      return new UsernamePasswordAuthenticationToken(auth.getName(),
          auth.getCredentials(), AUTHORITIES);
    }
    throw new BadCredentialsException("Bad Credentials");
  }
}

