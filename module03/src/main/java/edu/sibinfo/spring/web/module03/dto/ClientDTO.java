package edu.sibinfo.spring.web.module03.dto;

import lombok.Data;

@Data
public class ClientDTO {
	private Long id;
	private String familyName;
	private String firstName;
	private Iterable<PhoneDTO> phones;
}
