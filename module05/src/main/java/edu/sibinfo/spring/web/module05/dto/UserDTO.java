package edu.sibinfo.spring.web.module05.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDTO {

  @NotNull
  private String username;

  @NotNull
  private String password;

  private String confirm;

  @NotNull
  private String email;
}