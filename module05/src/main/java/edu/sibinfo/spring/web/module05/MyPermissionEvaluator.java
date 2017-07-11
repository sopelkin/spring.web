package edu.sibinfo.spring.web.module05;

import edu.sibinfo.spring.web.module05.dto.ClientDTO;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class MyPermissionEvaluator implements PermissionEvaluator {

  @Override
  public boolean hasPermission(Authentication authentication, Serializable targetId,
                               String targetType, Object permission) {
    if (authentication.getName().equals("admin") && targetType.contains("Client")) {
      return targetId instanceof Long && ((Long) targetId) % 2 == 0;
    }
    return true;
  }

  @Override
  public boolean hasPermission(Authentication authentication, Object targetDomainObject,
                               Object permission) {
    if (authentication.getName().equals("admin") && targetDomainObject instanceof ClientDTO) {
      return ((ClientDTO) targetDomainObject).getId() % 2 == 0;
    }
    return true;
  }
}
