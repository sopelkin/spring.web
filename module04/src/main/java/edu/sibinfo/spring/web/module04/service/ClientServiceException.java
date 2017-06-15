package edu.sibinfo.spring.web.module04.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClientServiceException extends RuntimeException {
	private static final long serialVersionUID = -3292013509335162440L;
	private final int code;
	private final String message;
}
