package edu.sibinfo.spring.web.module05.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import edu.sibinfo.spring.web.module05.service.ClientRegisteredEvent;

@Service
public class SmsService {
	private static final Logger log = LoggerFactory.getLogger(SmsService.class);

	@EventListener
	public void sendRegistrationNotification(ClientRegisteredEvent event) {
		log.info("{} : \"{}, you were registered\"", 
				event.getPhone().getNumber(), event.getClient().getName());
	}
}
