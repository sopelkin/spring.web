package edu.sibinfo.spring.web.module05.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PasswordResetToken {

  //TODO: configuration value
  private static final int EXPIRATION = 60 * 24;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String token;

  @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  private User user;

  private LocalDateTime expiryDate;

  public PasswordResetToken(final String token, final User user) {
    this.token = token;
    this.user = user;
    this.expiryDate = calculateExpiryDate(EXPIRATION);
  }

  private LocalDateTime calculateExpiryDate(final int expiry) {
    return LocalDateTime.now().plusMinutes(expiry);
  }
}
