package edu.sibinfo.spring.web.module05.controller;

import edu.sibinfo.spring.web.module05.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultiAuthContoller {

  private final NotificationService notificationService;

  @Autowired
  public MultiAuthContoller(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @RequestMapping("/ping")
  public String ping() {
    return "OK";
  }

  @Secured({"ROLE_USER", "RUN_AS_AUDITOR"})
  @RequestMapping("/runAs")
  public String tryRunAs() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String before = auth.getAuthorities().toString();
    notificationService.reporting();
    String aftter = auth.getAuthorities().toString();
    return "Current User Authorities inside this RunAS method only " +
        before + " and after call " + aftter;
  }

  @RequestMapping("/roles")
  public String listRoles() {
    return "Current user authorities " + SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
  }
}
