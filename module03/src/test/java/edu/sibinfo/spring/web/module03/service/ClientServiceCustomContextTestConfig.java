package edu.sibinfo.spring.web.module03.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

import edu.sibinfo.spring.web.module03.AppRunner;
import edu.sibinfo.spring.web.module03.service.impl.SmsService;

@TestConfiguration
public class ClientServiceCustomContextTestConfig {
	@MockBean
	private SmsService smsService;
	@MockBean
	private AppRunner appRunner;
}
