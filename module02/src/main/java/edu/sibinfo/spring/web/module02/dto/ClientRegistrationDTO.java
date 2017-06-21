package edu.sibinfo.spring.web.module02.dto;

import lombok.Data;

@Data
public class ClientRegistrationDTO {
	private String familyName;
	private String firstName;
	private String registrationPhone;
}
