package edu.sibinfo.spring.web.module05.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sibinfo.spring.web.module05.AppRunner;
import edu.sibinfo.spring.web.module05.dao.PhoneType;
import edu.sibinfo.spring.web.module05.dto.ClientDTO;
import edu.sibinfo.spring.web.module05.dto.ClientRegistrationDTO;
import edu.sibinfo.spring.web.module05.service.ClientService;
import edu.sibinfo.spring.web.module05.service.impl.SmsService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@Transactional
public class ClientServiceWebApiNoWebServerTests {

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
  public void getNoId() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/client/get?id=8"))
        .andExpect(status().is5xxServerError())
        .andExpect(jsonPath("$.code").value(1))
        .andExpect(jsonPath("$.message").value("Client doesn't exist: ID=" + 8));
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
