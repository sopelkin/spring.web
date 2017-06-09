package edu.sibinfo.spring.web.module02.dto;

import lombok.Data;

@Data
public class ClientDTO {
	private Long id;
	private String familyName;
	private String firstName;
	private Iterable<PhoneDTO> phones;
}
