package edu.sibinfo.spring.web.module04.dto;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "register")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegistrationDTO {
	private String familyName;
	private String firstName;
	private String registrationPhone;
}
