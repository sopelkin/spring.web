package edu.sibinfo.spring.web.module05.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement(name = "register")
@Data
public class ClientRegistrationDTO {
	@Size(min=2, max=25)
	@Pattern(regexp="\\p{L}+")
	private String familyName;
	
	@Size(min=2, max=20)
	@Pattern(regexp="\\p{L}+")
	private String firstName;
	
	@Pattern(regexp="9\\d{9}")
	private String registrationPhone;
}
