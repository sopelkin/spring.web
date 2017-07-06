package edu.sibinfo.spring.web.module05.job;

import edu.sibinfo.spring.web.module05.dao.PasswordResetTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
@Transactional
public class ClearExpiredTokensJob {
  private static final Logger log = LoggerFactory.getLogger(ClearExpiredTokensJob.class);

  final private PasswordResetTokenRepository passwordTokenRepository;

  @Autowired
  public ClearExpiredTokensJob(PasswordResetTokenRepository passwordTokenRepository) {
    this.passwordTokenRepository = passwordTokenRepository;
  }

  //TODO: configuration value
//  @Scheduled(fixedRate = 60 * 1000)
  public void purgeExpired() {
    log.info("ClearExpiredTokensJob is running");
    passwordTokenRepository.deleteAllExpiredSince(LocalDateTime.now());
  }
}
