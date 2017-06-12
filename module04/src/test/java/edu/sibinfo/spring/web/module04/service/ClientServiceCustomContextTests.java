package edu.sibinfo.spring.web.module04.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.sibinfo.spring.web.module04.dao.ClientDao;
import edu.sibinfo.spring.web.module04.dao.PhoneType;
import edu.sibinfo.spring.web.module04.domain.Client;
import edu.sibinfo.spring.web.module04.domain.Phone;
import edu.sibinfo.spring.web.module04.dto.ClientDTO;
import edu.sibinfo.spring.web.module04.dto.PhoneDTO;
import edu.sibinfo.spring.web.module04.service.impl.SmsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ContextConfiguration(classes=ClientServiceCustomContextTestConfig.class)
public class ClientServiceCustomContextTests {
	
    private static final String FAMILY_NAME = "Фамилия";
	private static final String MOBILE_PHONE = "+701010101";
	private static final String HOME_PHONE = "+702020202";
	private static final String OFFICE_PHONE = "+702020202";
	
	@Autowired
    private ClientService service;
    @Autowired
    private ClientDao dao;
    @Autowired
    private SmsService smsService;
    
    @Test
    public void clientRegisters() {
        registerClient("A", "B", "7");
    }

    @Test
    public void clientRegistersCyrillic() {
        registerClient("А", "Б", "+7");
    }

	private Client registerClient(String firstName, String familyName, String mobile) {
		ClientDTO client = service.register(firstName, familyName, mobile);
        assertEquals(1L, dao.count());
        Client realClient = dao.findOne(client.getId());
        assertNotNull(realClient);
        assertEquals(firstName, realClient.getFirstName());
        assertEquals(familyName, realClient.getFamilyName());
        
        List<Phone> phones = realClient.getPhones();
        assertEquals(1L, phones.size());
        checkPhone(mobile, PhoneType.MOBILE, phones.get(0));
        
        ArgumentCaptor<ClientRegisteredEvent> captor = ArgumentCaptor.forClass(ClientRegisteredEvent.class); 
        verify(smsService).sendRegistrationNotification(captor.capture());
        assertSame(client, captor.getValue().getClient());
        checkPhone(mobile, PhoneType.MOBILE, captor.getValue().getPhone());
        return realClient;
	}

	@Test 
	public void addPhonesAllTypes() {
		ClientDTO client = registerClientWith3Phones();
		checkClientWith3Phones(client);
	}
	
	private static void checkPhone(String number, PhoneType phoneType, Phone phone) {
		assertNotNull(phone);
        assertEquals(number, phone.getNumber());
        assertEquals(phoneType, phone.getPhoneType());
	}
	
	private static void checkPhone(String number, PhoneType phoneType, PhoneDTO phone) {
		assertNotNull(phone);
        assertEquals(number, phone.getNumber());
        assertEquals(phoneType.name(), phone.getPhoneType());
	}
	
	private ClientDTO registerClientWith3Phones() {
		ClientDTO client = service.register("Три", FAMILY_NAME, MOBILE_PHONE);
		service.addPhone(client, HOME_PHONE, PhoneType.HOME);
		service.addPhone(client, OFFICE_PHONE, PhoneType.OFFICE);
		return client;
	}
	
	private void checkClientWith3Phones(ClientDTO client) {
		Client realClient = dao.findOne(client.getId());
        List<Phone> phones = realClient.getPhones();
        assertEquals(3L, phones.size());
        checkPhone(MOBILE_PHONE, PhoneType.MOBILE, phones.get(0));
        checkPhone(HOME_PHONE, PhoneType.HOME, phones.get(1));
        checkPhone(OFFICE_PHONE, PhoneType.OFFICE, phones.get(2));
	}
	
	@Test
	public void findClientByPhone() {
		registerClientWith3Phones();
		checkClientWith3Phones(service.findByPhone(MOBILE_PHONE));
	}
	
	@Test
	public void findClientByFamilyName() {
		registerClientWith3Phones();
		checkClientWith3Phones(service.findByFamilyName(FAMILY_NAME));
	}
	
	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void findClientByFamilyNameAndConsume() {
		ClientDTO client = registerClientWith3Phones();
		service.findByFamilyName(FAMILY_NAME, 
				c -> c.getPhones().forEach(
						p -> assertNotNull(p.getNumber())));
		service.deleteClient(client); // Required: not a transactional method
	}
	
	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void registerAndDeleteClient() {
		ClientDTO client = registerClientWith3Phones();
		service.deleteClient(client);
		assertEquals(0L, dao.count());
	}
}
