package edu.sibinfo.spring.web.module05.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement(name="client")
@Data
public class ClientDTO {
	private Long id;
	private String lastName;
	private String name;
	private List<PhoneDTO> phones;
}
