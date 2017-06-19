package edu.sibinfo.spring.web.module04;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import edu.sibinfo.spring.web.module04.dao.PhoneType;
import edu.sibinfo.spring.web.module04.dto.ClientDTO;
import edu.sibinfo.spring.web.module04.dto.ClientRegistrationDTO;
import edu.sibinfo.spring.web.module04.dto.PhoneDTO;

@Component
public class AppRunner implements ApplicationRunner {

	private final RestTemplate restTemplate;

	@Autowired
	public AppRunner(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		register("Luke", "Williams", "+79239889523");
		register("Sam", "Williams", "+79053495805");
		ClientDTO johnSmith = register("John", "Smith", "+79132354313");
		addPhone(johnSmith, "+79132350013", PhoneType.OFFICE);
		
		register("Joel", "Williams", "+79069409806");
		register("Lucas", "Williams", "+79074452307");
		ClientDTO lukeSmith = register("Luke", "Smith", "+79089872308");
		addPhone(lukeSmith, "+79089870088", PhoneType.OFFICE);
		addPhone(lukeSmith, "+73832870088", PhoneType.HOME);
		
        register("Sawyer", "Smith", "+79239000023");
		register("John", "Williams", "+79132000013");
		ClientDTO samSmith = register("Sam", "Smith", "+79053400005");
		addPhone(samSmith, "+73826700015", PhoneType.HOME);
		addPhone(samSmith, "+79053400025", PhoneType.OFFICE);
		addPhone(samSmith, "+79053400035", PhoneType.MOBILE);
		
		register("Joel", "Smith", "+79069450006");
		register("Lucas", "Williams", "+79074470007");
		register("Sawyer", "Williams", "+790898a60008");
	}
	
	private ClientDTO register(String firstName, String familyName, String phone) throws URISyntaxException {
		RequestEntity<ClientRegistrationDTO> request = RequestEntity
			.post(new URI("http://localhost:8080/api/client/register"))
			.body(new ClientRegistrationDTO(familyName, firstName, phone));
		return restTemplate.exchange(request, ClientDTO.class).getBody();
	}

	private ClientDTO addPhone(ClientDTO client, String number, PhoneType phoneType) throws URISyntaxException {
		URI uri = UriComponentsBuilder
				.fromHttpUrl("http://localhost:8080/api/client/addPhone")
				.queryParam("id", client.getId()).build().toUri();
		RequestEntity<PhoneDTO> request = RequestEntity
				.post(uri)
				.body(new PhoneDTO(number, phoneType.name()));
		return restTemplate.exchange(request, ClientDTO.class).getBody();
	}
}
