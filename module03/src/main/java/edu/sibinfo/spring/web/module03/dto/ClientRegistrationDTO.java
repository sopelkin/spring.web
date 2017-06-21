package edu.sibinfo.spring.web.module03.dto;

import lombok.Data;

@Data
public class ClientRegistrationDTO {
	private String familyName;
	private String firstName;
	private String registrationPhone;
}
