package edu.sibinfo.spring.web.module05.controller;

import edu.sibinfo.spring.web.module05.domain.User;
import edu.sibinfo.spring.web.module05.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(value = "/registration", method = RequestMethod.GET)
  public String registration(Model model) {
    model.addAttribute("userForm", new User());
    return "registration";
  }

  @RequestMapping(value = "/registration", method = RequestMethod.POST)
  public String registration(@ModelAttribute("userForm") User userForm, Model model) {
    userService.save(userForm);
    userService.autologin(userForm.getUsername(), userForm.getPassword());
    return "redirect:/";
  }
}