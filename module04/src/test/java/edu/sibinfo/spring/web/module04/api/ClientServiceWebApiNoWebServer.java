package edu.sibinfo.spring.web.module04.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sibinfo.spring.web.module04.AppRunner;
import edu.sibinfo.spring.web.module04.dao.PhoneType;
import edu.sibinfo.spring.web.module04.dto.ClientDTO;
import edu.sibinfo.spring.web.module04.dto.ClientRegistrationDTO;
import edu.sibinfo.spring.web.module04.service.ClientService;
import edu.sibinfo.spring.web.module04.service.impl.SmsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClientServiceWebApiNoWebServer {

	private static final String FAMILY_NAME = "LastName";
	private static final String FIRST_NAME = "FirstName";
	private static final String MOBILE_PHONE = "+701010101";
	private static final String HOME_PHONE = "+702020202";
	private static final String OFFICE_PHONE = "+703030303";

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(); 

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AppRunner appRunner;
	@MockBean
	private SmsService smsService;

	@Autowired
	private ClientService clientService;

	@Test
	public void get() throws Exception {
		ClientDTO client = clientService.register(FIRST_NAME, FAMILY_NAME, MOBILE_PHONE);
		clientService.addPhone(client, OFFICE_PHONE, PhoneType.OFFICE);
		clientService.addPhone(client, HOME_PHONE, PhoneType.HOME);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/client/get?id=" + client.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id").value(client.getId()))
				.andExpect(jsonPath("$.lastName").value(FAMILY_NAME)).andExpect(jsonPath("$.name").value(FIRST_NAME))
				.andExpect(jsonPath("$.phones[0].number").value(MOBILE_PHONE))
				.andExpect(jsonPath("$.phones[0].phoneType").value(PhoneType.MOBILE.name()))
				.andExpect(jsonPath("$.phones[1].number").value(OFFICE_PHONE))
				.andExpect(jsonPath("$.phones[1].phoneType").value(PhoneType.OFFICE.name()))
				.andExpect(jsonPath("$.phones[2].number").value(HOME_PHONE))
				.andExpect(jsonPath("$.phones[2].phoneType").value(PhoneType.HOME.name()));
	}

    @Test
    public void register() throws Exception {
    	ClientRegistrationDTO registrationDTO = new ClientRegistrationDTO();
    	registrationDTO.setFamilyName(FAMILY_NAME);
    	registrationDTO.setFirstName(FIRST_NAME);
    	registrationDTO.setRegistrationPhone(MOBILE_PHONE);
    	String json = OBJECT_MAPPER.writeValueAsString(registrationDTO);

    	mockMvc.perform(MockMvcRequestBuilders.post("/api/client/register").content(json).contentType(MediaType.APPLICATION_JSON))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		   .andExpect(jsonPath("$.id").isNumber())
		   .andExpect(jsonPath("$.lastName").value(FAMILY_NAME))
		   .andExpect(jsonPath("$.phones[0].number").value(MOBILE_PHONE))
		   .andExpect(jsonPath("$.phones[0].phoneType").value(PhoneType.MOBILE.name()));
    }	
}
