package edu.sibinfo.spring.web.module05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

  @RequestMapping("/")
  public String welocme(Model model) {
    return "redirect:client/all";
  }

  @RequestMapping("/login")
  public String login() {
    return "login";
  }

  @RequestMapping("/login-err")
  public String loginError(Model model) {
    model.addAttribute("loginError", true);
    return "login";
  }

  @RequestMapping("/logout")
  public String logout() {
    return "redirect:login";
  }
}
