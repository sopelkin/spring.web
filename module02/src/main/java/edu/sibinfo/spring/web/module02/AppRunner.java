package edu.sibinfo.spring.web.module02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import edu.sibinfo.spring.web.module02.service.ClientService;

@Component
public class AppRunner implements ApplicationRunner {
	Logger log = LoggerFactory.getLogger(AppRunner.class);
	private final ClientService clientService;

	@Autowired
	public AppRunner(ClientService clientService) {
		this.clientService = clientService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		clientService.register("Luke", "Williams", "+79239889523");
		clientService.register("John", "Smith", "+79132354313");
		clientService.register("Sam", "Williams", "+79053495805");
		
		clientService.register("Joel", "Williams", "+79069409806");
		clientService.register("Lucas", "Williams", "+79074452307");
		clientService.register("Sawyer", "Smith", "+79089872308");
		
		clientService.register("Luke", "Smith", "+79239000023");
		clientService.register("John", "Williams", "+79132000013");
		clientService.register("Sam", "Smith", "+79053400005");
		
		clientService.register("Joel", "Smith", "+79069450006");
		clientService.register("Lucas", "Williams", "+79074470007");
		clientService.register("Sawyer", "Williams", "+79089860008");
	}
}
