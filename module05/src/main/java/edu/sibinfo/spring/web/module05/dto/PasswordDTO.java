package edu.sibinfo.spring.web.module05.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDTO {
  @NotNull
  private String password;
  @NotNull
  private String confirm;
}