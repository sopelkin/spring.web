package edu.sibinfo.spring.web.module02.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.function.Consumer;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.sibinfo.spring.web.module02.dao.ClientDao;
import edu.sibinfo.spring.web.module02.dao.PhoneType;
import edu.sibinfo.spring.web.module02.domain.Client;
import edu.sibinfo.spring.web.module02.domain.Phone;
import edu.sibinfo.spring.web.module02.dto.ClientDTO;
import edu.sibinfo.spring.web.module02.service.ClientRegisteredEvent;
import edu.sibinfo.spring.web.module02.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientDao clientDao;
	private final MessageDigest encoder;
	private final ApplicationEventPublisher publisher;

	@Autowired
	public ClientServiceImpl(ClientDao clientDao, MessageDigest encoder, ApplicationEventPublisher publisher) {
		super();
		this.clientDao = clientDao;
		this.encoder = encoder;
		this.publisher = publisher;
	}

	@Override
	public Client register(String firstName, String familyName, String mobile) {
		Client client = new Client(familyName, firstName);
		clientDao.save(client);
		Phone phone = addPhone(client, mobile, PhoneType.MOBILE);
		publisher.publishEvent(new ClientRegisteredEvent(client, phone));
		return client;
	}
	
	@Override
	public Phone addPhone(Client client, String number, PhoneType phoneType) {
		Phone phone = new Phone(number, phoneType);
		client.addPhone(phone);
		clientDao.save(client);
		return phone;
	}

	@Override
	public void setPassword(Client client, String password) {
		client.setPasswordEncoded(encoder.digest(password.getBytes(StandardCharsets.UTF_8)));
		clientDao.save(client);		
	}

	@Override
	public void deleteClient(Client client) {
		clientDao.delete(client.getId()); // clientDao.delete(client) fails in non-transactional context 
	}

	@Override
	public Client findByPhone(String number) {
		return clientDao.findByPhone(number);
	}

	@Override
	public Client findByFamilyName(String familyName) {
		return clientDao.findByFamilyName(familyName);
	}

	@Transactional
	@Override
	public Client findByFamilyName(String familyName, Consumer<Client> consumer) {
		Client c = clientDao.findByFamilyName(familyName);
		consumer.accept(c);
		return c;
	}

	@Override
	public Iterable<Client> search(String charactersitics) {
		return clientDao.search(charactersitics);
	}

	@Override
	public Page<Client> findAll(int page) {
		return clientDao.findAll(new PageRequest(page, 10, new Sort("familyName", "firstName")));
	}

	@Override
	public ClientDTO findOne(long clientId) {
		Client client = clientDao.findOne(clientId);
		ClientDTO dto = new ClientDTO();
		dto.setFamilyName(client.getFamilyName());
		dto.setFirstName(client.getFirstName());
		dto.setId(client.getId());
		return dto;
	}

	@Override
	@Transactional
	public void update(ClientDTO dto) {
		Client client = clientDao.findOne(dto.getId());
		client.setFamilyName(dto.getFamilyName());
		client.setFirstName(dto.getFirstName());
	}
}
