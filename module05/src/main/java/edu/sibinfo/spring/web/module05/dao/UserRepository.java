package edu.sibinfo.spring.web.module05.dao;

import edu.sibinfo.spring.web.module05.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}
