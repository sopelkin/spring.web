package edu.sibinfo.spring.web.module05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

	@RequestMapping("/")
	public String welocme(Model model) {
		return "redirect:client/all";
	}
}
