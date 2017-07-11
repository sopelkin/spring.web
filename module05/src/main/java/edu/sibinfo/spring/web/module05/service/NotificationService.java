package edu.sibinfo.spring.web.module05.service;

import edu.sibinfo.spring.web.module05.domain.PasswordResetToken;
import edu.sibinfo.spring.web.module05.domain.User;
import org.springframework.security.access.prepost.PreAuthorize;

public interface NotificationService {
  void sendPasswordReset(String appUrl, PasswordResetToken token, User user);

  @PreAuthorize("hasRole('ROLE_RUN_AS_AUDITOR')")
  void reporting();
}
