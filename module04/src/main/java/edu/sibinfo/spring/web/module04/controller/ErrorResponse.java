package edu.sibinfo.spring.web.module04.controller;

import javax.xml.bind.annotation.XmlRootElement;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;

@XmlRootElement
@Getter
@AllArgsConstructor
@ToString
public class ErrorResponse {
	private final int code;
	private final String message;
}
