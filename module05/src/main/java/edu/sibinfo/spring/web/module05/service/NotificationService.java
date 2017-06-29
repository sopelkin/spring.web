package edu.sibinfo.spring.web.module05.service;

import edu.sibinfo.spring.web.module05.domain.PasswordResetToken;
import edu.sibinfo.spring.web.module05.domain.User;

public interface NotificationService {
  void sendPasswordReset(String appUrl, PasswordResetToken token, User user);
}
