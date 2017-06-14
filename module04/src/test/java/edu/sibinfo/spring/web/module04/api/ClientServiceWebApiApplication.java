package edu.sibinfo.spring.web.module04.api;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import edu.sibinfo.spring.web.module04.TestUtils;
import edu.sibinfo.spring.web.module04.dao.PhoneType;
import edu.sibinfo.spring.web.module04.dto.ClientDTO;
import edu.sibinfo.spring.web.module04.dto.ClientRegistrationDTO;
import edu.sibinfo.spring.web.module04.dto.PhoneDTO;
import edu.sibinfo.spring.web.module04.service.ClientService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClientServiceWebApiApplication {

    private static final String FAMILY_NAME = "LastName";
    private static final String FIRST_NAME = "FirstName";
	private static final String MOBILE_PHONE = "+701010101";
	private static final String HOME_PHONE = "+702020202";
	
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ClientService clientService;
    
    @Test
    public void register() {
    	ClientRegistrationDTO registrationDto = new ClientRegistrationDTO();
    	registrationDto.setFamilyName(FAMILY_NAME);
    	registrationDto.setFirstName(FIRST_NAME);
    	registrationDto.setRegistrationPhone(MOBILE_PHONE);
    	
    	ClientDTO dto = restTemplate.postForObject(
    			composeUrl(port, "register"),
    			registrationDto, ClientDTO.class);
    			
		assertClient(dto);
		assertRegistrationPhone(dto.getPhones());
    }

    @Test
    public void getSuccessfull() {
    	ClientDTO client = clientService.register(FIRST_NAME, FAMILY_NAME, MOBILE_PHONE);
    	ClientDTO dto = restTemplate.getForObject(composeUrl(port, "get?id=" + client.getId()), ClientDTO.class);
    	assertClient(dto);
		assertRegistrationPhone(dto.getPhones());
    }

    @Test
    public void updateSuccessfull() {
    	ClientDTO client = clientService.register("empty", "almostEmty", MOBILE_PHONE);
    	client.setLastName(FAMILY_NAME);
    	client.setName(FIRST_NAME);
    	ClientDTO dto = restTemplate.postForObject(composeUrl(port, "update"), client, ClientDTO.class);
    	assertClient(dto);
		assertRegistrationPhone(dto.getPhones());
    }
    
    @Test
    public void addPhoneSuccessfull() {
    	ClientDTO client = clientService.register(FIRST_NAME, FAMILY_NAME, MOBILE_PHONE);
    	PhoneDTO additionalPhone = new PhoneDTO(HOME_PHONE, PhoneType.HOME.name());
    	ClientDTO dto = restTemplate.postForObject(composeUrl(port, "addPhone?id=" + client.getId()), additionalPhone, ClientDTO.class);
    	assertClient(dto);
	
    	List<PhoneDTO> list = dto.getPhones();
		assertEquals(2, list.size());
		TestUtils.assertPhone(list.get(0), MOBILE_PHONE, PhoneType.MOBILE);
		TestUtils.assertPhone(list.get(1), HOME_PHONE, PhoneType.HOME);
    }
    
    private String composeUrl(int port, String method) {
		return String.format("http://localhost:%d/api/client/%s", port, method);
	}

	private void assertClient(ClientDTO dto) {
		assertNotNull(dto);
		assertNotNull(dto.getId());
		assertEquals(FAMILY_NAME, dto.getLastName());
		assertEquals(FIRST_NAME, dto.getName());
	}

	private void assertRegistrationPhone(List<PhoneDTO> list) {
		assertEquals(1, list.size());
		TestUtils.assertPhone(list.get(0), MOBILE_PHONE, PhoneType.MOBILE);
	}
	
}
