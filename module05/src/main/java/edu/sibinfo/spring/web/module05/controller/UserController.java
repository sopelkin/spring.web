package edu.sibinfo.spring.web.module05.controller;

import edu.sibinfo.spring.web.module05.domain.PasswordResetToken;
import edu.sibinfo.spring.web.module05.domain.User;
import edu.sibinfo.spring.web.module05.dto.PasswordDTO;
import edu.sibinfo.spring.web.module05.dto.UserDTO;
import edu.sibinfo.spring.web.module05.exception.ReCaptchaInvalidException;
import edu.sibinfo.spring.web.module05.exception.UserAlreadyExistException;
import edu.sibinfo.spring.web.module05.service.CaptchaService;
import edu.sibinfo.spring.web.module05.service.NotificationService;
import edu.sibinfo.spring.web.module05.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {
  private final UserService userService;
  private final ConversionService conversionService;
  private final NotificationService notificationService;
  private final CaptchaService captchaService;

  @Autowired
  public UserController(UserService userService, ConversionService conversionService, NotificationService notificationService, CaptchaService captchaService) {
    this.userService = userService;
    this.conversionService = conversionService;
    this.notificationService = notificationService;
    this.captchaService = captchaService;
  }

  @GetMapping("/registration")
  public String registration(Model model) {
    model.addAttribute("userForm", new UserDTO());
    model.addAttribute("captchaSiteKey", captchaService.getReCaptchaSite());
    return "registration";
  }

  @PostMapping("/registration")
  public String registration(@ModelAttribute("userForm") @Valid final UserDTO userForm, final HttpServletRequest request) throws UserAlreadyExistException, ReCaptchaInvalidException {
    final String response = request.getParameter("g-recaptcha-response");
    captchaService.processResponse(response, getClientIP(request));
    //Если не поймали исключение то все хорошо, можно регистрировать.

    userService.registerAccount(userForm);
    userService.autologin(userForm.getUsername(), userForm.getPassword());
    return "redirect:/";
  }

  @GetMapping(value = "/resetPassword")
  public String resetPassword() {
    return "resetPassword";
  }

  @PostMapping(value = "/resetPassword")
  public String resetPassword(HttpServletRequest request, @RequestParam("username") final String username, Model model) {
    final User user = userService.findByUsername(username);
    if (user != null) {
      PasswordResetToken token = userService.createPasswordResetToken(user);
      notificationService.sendPasswordReset(getAppUrl(request), token, user);
      model.addAttribute("message", "Ссылка на страницу сброса пароля отправлена.");
    } else {
      model.addAttribute("message", "Пользователь не найден.");
    }
    return "resetPassword";
  }

  @GetMapping(value = "/changePassword")
  public String showChangePasswordPage(final Model model, @RequestParam("token") final String token) {
    final User user = userService.findUserByResetToken(token);
    if (user == null) {
      return "redirect:/login";
    }
    userService.autologin(user.getUsername(), user.getPassword());
    model.addAttribute("passwordForm", new PasswordDTO());
    return "changePassword";
  }

  @PostMapping(value = "/savePassword")
  public String savePassword(@Valid PasswordDTO passwordDto) {
    userService.changeCurrentUserPassword(passwordDto.getPassword());
    return "redirect:/";
  }

  private String getAppUrl(HttpServletRequest request) {
    return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
  }

  private String getClientIP(HttpServletRequest request) {
    final String xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader == null) {
      return request.getRemoteAddr();
    }
    return xfHeader.split(",")[0];
  }
}