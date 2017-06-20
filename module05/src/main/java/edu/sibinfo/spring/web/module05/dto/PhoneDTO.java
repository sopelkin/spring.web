package edu.sibinfo.spring.web.module05.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {
	private String number; 
	private String phoneType;
}
