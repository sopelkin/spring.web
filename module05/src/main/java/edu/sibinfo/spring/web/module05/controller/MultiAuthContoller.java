package edu.sibinfo.spring.web.module05.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultiAuthContoller {
	
	@RequestMapping("/ping")
	public String ping() {
		return "OK";
	}
}
