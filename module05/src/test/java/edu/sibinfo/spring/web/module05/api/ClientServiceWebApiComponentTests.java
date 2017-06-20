package edu.sibinfo.spring.web.module05.api;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sibinfo.spring.web.module05.controller.ClientApiController;
import edu.sibinfo.spring.web.module05.dao.PhoneType;
import edu.sibinfo.spring.web.module05.dto.ClientDTO;
import edu.sibinfo.spring.web.module05.dto.ClientRegistrationDTO;
import edu.sibinfo.spring.web.module05.dto.PhoneDTO;
import edu.sibinfo.spring.web.module05.service.ClientService;
import edu.sibinfo.spring.web.module05.service.ClientServiceException;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientApiController.class)
public class ClientServiceWebApiComponentTests {

    private static final String FAMILY_NAME = "LastName";
    private static final String FIRST_NAME = "FirstName";
	private static final String MOBILE_PHONE = "+701010101";
	private static final String HOME_PHONE = "+702020202";
	private static final String OFFICE_PHONE = "+702020202";
	private static final Long ID = 23452L;
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(); 
	
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ClientService clientService;
    
    @Test
    public void get() throws Exception {
    	ClientDTO clientDTO = createClient();
    	List<PhoneDTO> phones = new ArrayList<>(3);
    	phones.add(new PhoneDTO(MOBILE_PHONE, PhoneType.MOBILE.name()));
    	phones.add(new PhoneDTO(OFFICE_PHONE, PhoneType.OFFICE.name()));
    	phones.add(new PhoneDTO(HOME_PHONE, PhoneType.HOME.name()));
		clientDTO.setPhones(phones);
    	when(clientService.findOne(anyLong())).thenReturn(clientDTO);
    	
    	mockMvc.perform(MockMvcRequestBuilders.get("/api/client/get?id=1"))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		   .andExpect(jsonPath("$.id").value(ID))
		   .andExpect(jsonPath("$.lastName").value(FAMILY_NAME))
		   .andExpect(jsonPath("$.name").value(FIRST_NAME))
		   .andExpect(jsonPath("$.phones[0].number").value(MOBILE_PHONE))
		   .andExpect(jsonPath("$.phones[0].phoneType").value(PhoneType.MOBILE.name()))
		   .andExpect(jsonPath("$.phones[1].number").value(OFFICE_PHONE))
		   .andExpect(jsonPath("$.phones[1].phoneType").value(PhoneType.OFFICE.name()))
		   .andExpect(jsonPath("$.phones[2].number").value(HOME_PHONE))
		   .andExpect(jsonPath("$.phones[2].phoneType").value(PhoneType.HOME.name()));
    }
    
	@Test
	public void getNoId() throws Exception {
		int errorCode = 1;
		String errorMessage = "Error message";
    	when(clientService.findOne(anyLong())).thenThrow(new ClientServiceException(errorCode, errorMessage));
		mockMvc.perform(MockMvcRequestBuilders.get("/api/client/get?id=8"))
				.andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$.code").value(errorCode))
				.andExpect(jsonPath("$.message").value(errorMessage));
	}    
    @Test
    public void register() throws Exception {
    	ClientDTO clientDTO = createClient();
    	List<PhoneDTO> phones = new ArrayList<>(3);
    	phones.add(new PhoneDTO(MOBILE_PHONE, PhoneType.MOBILE.name()));
		clientDTO.setPhones(phones);

    	when(clientService.register(FIRST_NAME, FAMILY_NAME, MOBILE_PHONE)).thenReturn(clientDTO);

    	ClientRegistrationDTO registrationDTO = new ClientRegistrationDTO();
    	registrationDTO.setFamilyName(FAMILY_NAME);
    	registrationDTO.setFirstName(FIRST_NAME);
    	registrationDTO.setRegistrationPhone(MOBILE_PHONE);
    	String json = OBJECT_MAPPER.writeValueAsString(registrationDTO);

    	mockMvc.perform(MockMvcRequestBuilders.post("/api/client/register").content(json).contentType(MediaType.APPLICATION_JSON))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		   .andExpect(jsonPath("$.id").value(ID))
		   .andExpect(jsonPath("$.lastName").value(FAMILY_NAME))
		   .andExpect(jsonPath("$.phones[0].number").value(MOBILE_PHONE))
		   .andExpect(jsonPath("$.phones[0].phoneType").value(PhoneType.MOBILE.name()));
    }

	private ClientDTO createClient() {
		ClientDTO clientDTO = new ClientDTO();
    	clientDTO.setId(ID);
    	clientDTO.setLastName(FAMILY_NAME);
    	clientDTO.setName(FIRST_NAME);
		return clientDTO;
	}
}
