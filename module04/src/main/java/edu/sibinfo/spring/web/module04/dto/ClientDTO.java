package edu.sibinfo.spring.web.module04.dto;

import lombok.Data;

@Data
public class ClientDTO {
	private Long id;
	private String lastName;
	private String name;
	private Iterable<PhoneDTO> phones;
}
