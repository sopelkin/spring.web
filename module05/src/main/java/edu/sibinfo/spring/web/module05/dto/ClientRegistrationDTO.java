package edu.sibinfo.spring.web.module05.dto;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement(name = "register")
@Data
public class ClientRegistrationDTO {
	private String familyName;
	private String firstName;
	private String registrationPhone;
}
