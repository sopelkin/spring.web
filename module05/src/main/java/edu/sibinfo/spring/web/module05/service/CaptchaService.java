package edu.sibinfo.spring.web.module05.service;

import edu.sibinfo.spring.web.module05.exception.ReCaptchaInvalidException;

public interface CaptchaService {
  void processResponse(final String response, String ip) throws ReCaptchaInvalidException;

  String getReCaptchaSite();

  String getReCaptchaSecret();
}
