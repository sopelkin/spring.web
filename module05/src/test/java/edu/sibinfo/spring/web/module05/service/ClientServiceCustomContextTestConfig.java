package edu.sibinfo.spring.web.module05.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

import edu.sibinfo.spring.web.module05.AppRunner;
import edu.sibinfo.spring.web.module05.service.impl.SmsService;

@TestConfiguration
public class ClientServiceCustomContextTestConfig {
	@MockBean
	private SmsService smsService;
	@MockBean
	private AppRunner appRunner;
}
