package edu.sibinfo.spring.web.module05.exception;

public class UserAlreadyExistException extends Throwable {
  public UserAlreadyExistException(String message) {
    super(message);
  }
}
