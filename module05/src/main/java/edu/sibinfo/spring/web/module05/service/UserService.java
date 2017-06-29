package edu.sibinfo.spring.web.module05.service;

import edu.sibinfo.spring.web.module05.domain.PasswordResetToken;
import edu.sibinfo.spring.web.module05.domain.User;
import edu.sibinfo.spring.web.module05.dto.UserDTO;
import edu.sibinfo.spring.web.module05.exception.UserAlreadyExistException;

public interface UserService {
  void registerAccount(UserDTO userDTO) throws UserAlreadyExistException;

  User findByUsername(String username);

  void autologin(String username, String password);

  PasswordResetToken createPasswordResetToken(User user);

  User findUserByResetToken(String token);

  void changeCurrentUserPassword(String password);
}
