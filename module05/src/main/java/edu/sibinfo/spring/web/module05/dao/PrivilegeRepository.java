package edu.sibinfo.spring.web.module05.dao;

import edu.sibinfo.spring.web.module05.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
