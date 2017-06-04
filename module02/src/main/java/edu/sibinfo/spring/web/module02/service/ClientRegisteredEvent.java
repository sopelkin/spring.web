package edu.sibinfo.spring.web.module02.service;

import edu.sibinfo.spring.web.module02.domain.Client;
import edu.sibinfo.spring.web.module02.domain.Phone;

public class ClientRegisteredEvent {
	private final Client client;
	private final Phone phone;

	public ClientRegisteredEvent(Client client, Phone phone) {
		super();
		this.client = client;
		this.phone = phone;
	}

	public Client getClient() {
		return client;
	}

	public Phone getPhone() {
		return phone;
	}  
}
