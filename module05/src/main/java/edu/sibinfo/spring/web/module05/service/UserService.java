package edu.sibinfo.spring.web.module05.service;

import edu.sibinfo.spring.web.module05.domain.User;

public interface UserService {
  void save(User user);

  User findByUsername(String username);

  void autologin(String username, String password);
}
