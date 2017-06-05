package edu.sibinfo.spring.web.module02.service;

import java.util.function.Consumer;

import org.springframework.data.domain.Page;

import edu.sibinfo.spring.web.module02.dao.PhoneType;
import edu.sibinfo.spring.web.module02.domain.Client;
import edu.sibinfo.spring.web.module02.domain.Phone;

public interface ClientService {
	Client register(String firstName, String familyName, String phone);

	void setPassword(Client client, String password);

	Phone addPhone(Client client, String number, PhoneType phoneType);
	
	void deleteClient(Client client);
	
	Client findByPhone(String number);

	Client findByFamilyName(String familyName);
	
	Client findByFamilyName(String familyName, Consumer<Client> consumer);

	Iterable<Client> search(String charactersitics);

	Page<Client> findAll(int page);
}
