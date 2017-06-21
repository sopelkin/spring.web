package edu.sibinfo.spring.web.module05.api;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import edu.sibinfo.spring.web.module05.TestUtils;
import edu.sibinfo.spring.web.module05.controller.ErrorResponse;
import edu.sibinfo.spring.web.module05.dao.PhoneType;
import edu.sibinfo.spring.web.module05.dto.ClientDTO;
import edu.sibinfo.spring.web.module05.dto.ClientRegistrationDTO;
import edu.sibinfo.spring.web.module05.dto.PhoneDTO;
import edu.sibinfo.spring.web.module05.service.ClientService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClientServiceWebApiApplicationTests {

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
    public void getNoId() { // Id is not sent
    	ResponseEntity<ErrorResponse> entity = restTemplate.getForEntity(composeUrl(port, "get"), ErrorResponse.class);
    	assertBadRequest(entity);
    }
    
	@Test
    public void getBadId() { // An invalid client ID is sent
    	ResponseEntity<ErrorResponse> entity = restTemplate.getForEntity(composeUrl(port, "get?id=3897"), ErrorResponse.class);
    	assertNotFound(entity);
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
    public void updateNotFound() {
    	ClientDTO client = new ClientDTO();
    	client.setId(1298L);
    	ResponseEntity<ErrorResponse> entity = restTemplate.postForEntity(composeUrl(port, "update"), client, ErrorResponse.class);
    	assertNotFound(entity);
    }
    
    @Test
    public void updateBadRequest() {
    	ResponseEntity<ErrorResponse> entity = restTemplate.postForEntity(composeUrl(port, "update"), new PhoneDTO(), ErrorResponse.class);
    	assertServerError(entity);
    }
    
    @Test
    public void addPhoneSuccessfull() {
    	ClientDTO client = clientService.register(FIRST_NAME, FAMILY_NAME, MOBILE_PHONE);
    	PhoneDTO additionalPhone = createHomePhone();
    	ClientDTO dto = restTemplate.postForObject(composeUrl(port, "addPhone?id=" + client.getId()), additionalPhone, ClientDTO.class);
    	assertClient(dto);
	
    	List<PhoneDTO> list = dto.getPhones();
		assertEquals(2, list.size());
		TestUtils.assertPhone(list.get(0), MOBILE_PHONE, PhoneType.MOBILE);
		TestUtils.assertPhone(list.get(1), HOME_PHONE, PhoneType.HOME);
    }

    @Test
    public void addPhoneNotFound() {
    	PhoneDTO additionalPhone = createHomePhone();
    	ResponseEntity<ErrorResponse> entity = restTemplate.postForEntity(composeUrl(port, "addPhone?id=56747"), additionalPhone, ErrorResponse.class);
    	assertNotFound(entity);
    }
    
    @Test
    public void addPhoneBadRequest() {
    	ResponseEntity<ErrorResponse> entity = restTemplate.postForEntity(composeUrl(port, "addPhone"), new ClientDTO(), ErrorResponse.class);
    	assertBadRequest(entity);
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
	
    private void assertBadRequest(ResponseEntity<ErrorResponse> entity) {
		String message = entity.toString();
		assertTrue(message, entity.getStatusCode().is4xxClientError());
	}

	private void assertServerError(ResponseEntity<ErrorResponse> entity) {
		String message = entity.toString();
		assertTrue(message, entity.getStatusCode().is5xxServerError());
	}

	private void assertNotFound(ResponseEntity<ErrorResponse> entity) {
		String message = entity.toString();
		assertTrue(message, entity.getStatusCode().is5xxServerError());
    	ErrorResponse errorResponse = entity.getBody();
    	assertNotNull(message, errorResponse);
    	assertEquals(message, 1, errorResponse.getCode());
	}

	private PhoneDTO createHomePhone() {
		return new PhoneDTO(HOME_PHONE, PhoneType.HOME.name(), LocalDateTime.now());
	}    
}
