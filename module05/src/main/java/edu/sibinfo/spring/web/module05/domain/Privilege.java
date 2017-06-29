package edu.sibinfo.spring.web.module05.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@ToString(exclude = "roles")
public class Privilege {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "privileges")
  private Collection<Role> roles;
}
