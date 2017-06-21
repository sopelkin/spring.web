package edu.sibinfo.spring.web.module02.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import edu.sibinfo.spring.web.module02.EncoderConfiguration;
import edu.sibinfo.spring.web.module02.service.impl.ClientServiceImpl;
import edu.sibinfo.spring.web.module02.service.impl.SmsService;

@TestConfiguration
@Import({ ClientServiceImpl.class, EncoderConfiguration.class })
public class ClientServiceCustomContextTestConfig {
	@MockBean
	private SmsService smsService;

}
