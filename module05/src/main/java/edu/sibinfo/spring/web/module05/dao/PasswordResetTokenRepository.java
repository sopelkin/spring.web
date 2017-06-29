package edu.sibinfo.spring.web.module05.dao;

import edu.sibinfo.spring.web.module05.domain.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

  PasswordResetToken findByToken(String token);

  @Modifying
  @Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
  void deleteAllExpiredSince(LocalDateTime now);
}
