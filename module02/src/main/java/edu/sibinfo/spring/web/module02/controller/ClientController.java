package edu.sibinfo.spring.web.module02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sibinfo.spring.web.module02.domain.Client;
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

	@RequestMapping("/")
	public String all(Model model) {
		return all(0, model);
	}
	
	@RequestMapping("all")
	public String all(@RequestParam(name="page", required=false, defaultValue="0") int page, Model model) {
		Page<Client> clientsPage = clientService.findAll(page);
		model.addAttribute("clients", clientsPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("maxPage", clientsPage.getTotalPages());
		return "client/all";
	}
}
