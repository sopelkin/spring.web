package edu.sibinfo.spring.web.module04.service;

import java.util.function.Consumer;

import org.springframework.data.domain.Page;

import edu.sibinfo.spring.web.module04.dao.PhoneType;
import edu.sibinfo.spring.web.module04.domain.Client;
import edu.sibinfo.spring.web.module04.dto.ClientDTO;

public interface ClientService {
	ClientDTO register(String firstName, String familyName, String phone);

	void setPassword(ClientDTO client, String password);

	void addPhone(ClientDTO client, String number, PhoneType phoneType);
	
	void deleteClient(ClientDTO client);
	
	ClientDTO findByPhone(String number);

	ClientDTO findByFamilyName(String familyName);
	
	ClientDTO findByFamilyName(String familyName, Consumer<Client> consumer);

	Iterable<ClientDTO> search(String charactersitics);

	Page<ClientDTO> findAll(int page);

	ClientDTO findOne(long clientId);

	void update(ClientDTO dto);
}
