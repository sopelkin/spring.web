package edu.sibinfo.spring.web.module05.dao;

import edu.sibinfo.spring.web.module05.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Role getRoleByName(String name);
}