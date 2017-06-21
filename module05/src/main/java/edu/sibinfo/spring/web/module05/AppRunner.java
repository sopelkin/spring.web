package edu.sibinfo.spring.web.module05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import edu.sibinfo.spring.web.module05.dao.PhoneType;
import edu.sibinfo.spring.web.module05.dto.ClientDTO;
import edu.sibinfo.spring.web.module05.service.ClientService;

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
		clientService.register("Sam", "Williams", "+79053495805");
		ClientDTO johnSmith = clientService.register("John", "Smith", "+79132354313");
		clientService.addPhone(johnSmith, "+79132350013", PhoneType.OFFICE);
		
		clientService.register("Joel", "Williams", "+79069409806");
		clientService.register("Lucas", "Williams", "+79074452307");
		ClientDTO lukeSmith = clientService.register("Luke", "Smith", "+79089872308");
		clientService.addPhone(lukeSmith, "+79089870088", PhoneType.OFFICE);
		clientService.addPhone(lukeSmith, "+73832870088", PhoneType.HOME);
		
        clientService.register("Sawyer", "Smith", "+79239000023");
		clientService.register("John", "Williams", "+79132000013");
		ClientDTO samSmith = clientService.register("Sam", "Smith", "+79053400005");
		clientService.addPhone(samSmith, "+73826700015", PhoneType.HOME);
		clientService.addPhone(samSmith, "+79053400025", PhoneType.OFFICE);
		clientService.addPhone(samSmith, "+79053400035", PhoneType.MOBILE);
		
		clientService.register("Joel", "Smith", "+79069450006");
		clientService.register("Lucas", "Williams", "+79074470007");
		clientService.register("Sawyer", "Williams", "+79089860008");
	}
}
