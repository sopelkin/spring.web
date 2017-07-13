package edu.sibinfo.spring.web.module05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/")
public class DemoPagesController {

  @RequestMapping("links")
  public String getMultipleHttpLinksPage() {
    return "multiple/links";
  }

  @RequestMapping("admin")
  public String getAdminPage() {
    return "multiple/admin";
  }

  @RequestMapping("user/general")
  public String getUserPage() {
    return "multiple/user";
  }

  @RequestMapping("user/private")
  public String getPrivateUserPage() {
    return "multiple/private";
  }

  @RequestMapping("guest")
  public String getGuestPage() {
    return "multiple/guest";
  }

  @RequestMapping("login")
  public String getUserLoginPage() {
    return "multiple/login";
  }

  @RequestMapping("loginPrivate")
  public String getUserLoginPageWithWarning() {
    return "multiple/loginPrivate";
  }

  @RequestMapping("403")
  public String getAccessDeniedPage() {
    return "403";
  }
}