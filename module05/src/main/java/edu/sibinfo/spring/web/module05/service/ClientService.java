package edu.sibinfo.spring.web.module05.service;

import java.util.function.Consumer;

import org.springframework.data.domain.Page;

import edu.sibinfo.spring.web.module05.dao.PhoneType;
import edu.sibinfo.spring.web.module05.domain.Client;
import edu.sibinfo.spring.web.module05.dto.ClientDTO;

public interface ClientService {
	ClientDTO register(String firstName, String familyName, String phone);

	void setPassword(ClientDTO client, String password);

	ClientDTO addPhone(ClientDTO client, String number, PhoneType phoneType);
	
	void deleteClient(ClientDTO client);
	
	ClientDTO findByPhone(String number);

	ClientDTO findByFamilyName(String familyName);
	
	ClientDTO findByFamilyName(String familyName, Consumer<Client> consumer);

	Iterable<ClientDTO> search(String charactersitics);

	Page<ClientDTO> findAll(int page);

	ClientDTO findOne(long clientId);

	ClientDTO update(ClientDTO dto);
}
