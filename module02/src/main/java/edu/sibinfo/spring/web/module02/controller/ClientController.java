package edu.sibinfo.spring.web.module02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sibinfo.spring.web.module02.service.ClientService;

@Controller
@RequestMapping("client")
public class ClientController {
	private final ClientService clientService;
	
	@Autowired
	public ClientController(ClientService clientService) {
		super();
		this.clientService = clientService;
	}

	@RequestMapping("all")
	public String all(Model model) {
		model.addAttribute("clients", clientService.findAll());
		return "client/all";
	}
}
