package edu.sibinfo.spring.web.module04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

	private final ClientController clientController;
	
	@Autowired
	public ApplicationController(ClientController clientController) {
		super();
		this.clientController = clientController;
	}

	@RequestMapping("/")
	public String welocme(Model model) {
		return clientController.all(0, model);
	}
}
