package edu.sibinfo.spring.web.module05.exception;

public class ReCaptchaInvalidException extends Exception {
  public ReCaptchaInvalidException(String message) {
    super(message);
  }
}
