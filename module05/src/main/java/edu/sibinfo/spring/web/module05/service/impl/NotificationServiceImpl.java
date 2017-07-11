package edu.sibinfo.spring.web.module05.service.impl;

import edu.sibinfo.spring.web.module05.domain.PasswordResetToken;
import edu.sibinfo.spring.web.module05.domain.User;
import edu.sibinfo.spring.web.module05.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
  private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

  @Override
  public void sendPasswordReset(String appUrl, PasswordResetToken token, User user) {
    final String url = appUrl + "/changePassword?token=" + token.getToken();
    log.info("Password reset url: {} sent to {} ", url, user.getEmail());
  }

  @Override
  public void reporting() {
    log.info("AS AUDITOR: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
  }
}
