package edu.sibinfo.spring.web.module04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sibinfo.spring.web.module04.dto.ClientDTO;
import edu.sibinfo.spring.web.module04.dto.ClientRegistrationDTO;
import edu.sibinfo.spring.web.module04.service.ClientService;

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
		Page<ClientDTO> clientsPage = clientService.findAll(page);
		model.addAttribute("clients", clientsPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("maxPage", clientsPage.getTotalPages());
		return "client/all";
	}
	
	@PostMapping("register")
	public String register(@ModelAttribute ClientRegistrationDTO dto, Model model) {
		clientService.register(dto.getFirstName(), dto.getFamilyName(), dto.getRegistrationPhone());
		return all(0, model);
	}

	@GetMapping("edit")
	public String edit(@RequestParam(name="id") long clientId, Model model) {
		ClientDTO clientDto = clientService.findOne(clientId);
		model.addAttribute("client", clientDto);
		return "client/edit";
	}
	
	@PostMapping("edit")
	public String edit(@ModelAttribute ClientDTO dto, Model model) {
		clientService.update(dto);
		return all(model);
	}
}
