package edu.sibinfo.spring.web.module04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sibinfo.spring.web.module04.dao.PhoneType;
import edu.sibinfo.spring.web.module04.dto.ClientDTO;
import edu.sibinfo.spring.web.module04.dto.ClientRegistrationDTO;
import edu.sibinfo.spring.web.module04.dto.PhoneDTO;
import edu.sibinfo.spring.web.module04.service.ClientService;

@RestController
@RequestMapping("api/client")
public class ClientApiController {

	private final ClientService clientService;

	@Autowired
	public ClientApiController(ClientService clientService) {
		super();
		this.clientService = clientService;
	}

	@GetMapping(value = "get")
	public ClientDTO get(@RequestParam(name = "id") long clientId) {
		return clientService.findOne(clientId);
	}

	@PostMapping("register")
	public ClientDTO register(@RequestBody ClientRegistrationDTO dto) {
		return clientService.register(dto.getFirstName(), dto.getFamilyName(), dto.getRegistrationPhone());
	}

	@PostMapping("update")
	public ClientDTO update(@RequestBody ClientDTO dto) {
		return clientService.update(dto);
	}

	@PostMapping("addPhone")
	public ClientDTO addPhone(@RequestParam(name = "id") long id, @RequestBody PhoneDTO phone) {
		ClientDTO client = new ClientDTO();
		client.setId(id);
		return clientService.addPhone(client, phone.getNumber(), PhoneType.valueOf(phone.getPhoneType()));
	}	
}
