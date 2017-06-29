package edu.sibinfo.spring.web.module05.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@ToString
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String username;

  @Column(length = 60)
  private String password;

  private String email;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role",
      joinColumns =
      @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns =
      @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Collection<Role> roles;
}