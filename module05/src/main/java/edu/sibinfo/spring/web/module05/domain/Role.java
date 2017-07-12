package edu.sibinfo.spring.web.module05.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@ToString(exclude = "users")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @ManyToMany(mappedBy = "roles")
  private Collection<User> users;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "role_privilege",
      joinColumns =
      @JoinColumn(name = "role_id", referencedColumnName = "id"),
      inverseJoinColumns =
      @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
  private Collection<Privilege> privileges;

  private String name;
}