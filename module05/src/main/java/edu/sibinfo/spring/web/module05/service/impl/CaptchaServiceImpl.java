package edu.sibinfo.spring.web.module05.service.impl;

import edu.sibinfo.spring.web.module05.dto.GoogleResponse;
import edu.sibinfo.spring.web.module05.exception.ReCaptchaInvalidException;
import edu.sibinfo.spring.web.module05.service.CaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.net.URI;

@Service
public class CaptchaServiceImpl implements CaptchaService {
  private static final Logger log = LoggerFactory.getLogger(CaptchaServiceImpl.class);

  //TODO: extract to Configuration
  private static final String secret = "6Ld5oycUAAAAAMbBF7O58A1TYGcC51FcjVyfEClf";
  private static final String site = "6Ld5oycUAAAAAKODPtapEtRBpWagtjQUCC9pqKhI";

  private final RestOperations restTemplate;

  @Autowired
  public CaptchaServiceImpl(RestOperations restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public void processResponse(String response, String ip) throws ReCaptchaInvalidException {
    final URI verifyUri = URI.create(String.format("https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s", getReCaptchaSecret(), response, ip));
    final GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);
    log.debug("Google's response: {} ", googleResponse);

    if (!googleResponse.isSuccess()) {
      throw new ReCaptchaInvalidException("reCaptcha не валидна");
    }
  }

  @Override
  public String getReCaptchaSite() {
    return site;
  }

  @Override
  public String getReCaptchaSecret() {
    return secret;
  }
}
