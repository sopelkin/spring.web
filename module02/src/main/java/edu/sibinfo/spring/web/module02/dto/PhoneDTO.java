package edu.sibinfo.spring.web.module02.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhoneDTO {
	private String number; 
	private String phoneType;
}
