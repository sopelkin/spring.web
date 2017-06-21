package edu.sibinfo.spring.web.module02.service;

import edu.sibinfo.spring.web.module02.dto.ClientDTO;
import edu.sibinfo.spring.web.module02.dto.PhoneDTO;

public class ClientRegisteredEvent {
	private final ClientDTO client;
	private final PhoneDTO phone;

	public ClientRegisteredEvent(ClientDTO client, PhoneDTO phone) {
		super();
		this.client = client;
		this.phone = phone;
	}

	public ClientDTO getClient() {
		return client;
	}

	public PhoneDTO getPhone() {
		return phone;
	}  
}
