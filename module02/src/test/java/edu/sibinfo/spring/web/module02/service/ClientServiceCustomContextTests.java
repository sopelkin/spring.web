package edu.sibinfo.spring.web.module02.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.sibinfo.spring.web.module02.dao.ClientDao;
import edu.sibinfo.spring.web.module02.dao.PhoneType;
import edu.sibinfo.spring.web.module02.domain.Client;
import edu.sibinfo.spring.web.module02.domain.Phone;
import edu.sibinfo.spring.web.module02.service.impl.SmsService;

@RunWith(SpringRunner.class)
@DataJpaTest
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
		Client client = service.register(firstName, familyName, mobile);
        assertEquals(1L, dao.count());
        Client realClient = dao.findOne(client.getId());
        assertNotNull(realClient);
        assertEquals(firstName, realClient.getFirstName());
        assertEquals(familyName, realClient.getFamilyName());
        
        List<Phone> phones = realClient.getPhones();
        assertEquals(1L, phones.size());
        checkPhone(mobile, phones.get(0), PhoneType.MOBILE);
        
        ArgumentCaptor<ClientRegisteredEvent> captor = ArgumentCaptor.forClass(ClientRegisteredEvent.class); 
        verify(smsService).sendRegistrationNotification(captor.capture());
        assertSame(client, captor.getValue().getClient());
        return realClient;
	}

	@Test 
	public void addPhonesAllTypes() {
		Client client = registerClientWith3Phones();
		checkClientWith3Phones(client);
	}
	
	private void checkPhone(String number, Phone phone, PhoneType phoneType) {
		assertNotNull(phone);
        assertEquals(number, phone.getNumber());
        assertEquals(phoneType, phone.getPhoneType());
	}
	
	private Client registerClientWith3Phones() {
		Client client = service.register("Три", FAMILY_NAME, MOBILE_PHONE);
		service.addPhone(client, HOME_PHONE, PhoneType.HOME);
		service.addPhone(client, OFFICE_PHONE, PhoneType.OFFICE);
		return client;
	}
	
	private void checkClientWith3Phones(Client client) {
		Client realClient = dao.findOne(client.getId());
        List<Phone> phones = realClient.getPhones();
        assertEquals(3L, phones.size());
        checkPhone(MOBILE_PHONE, phones.get(0), PhoneType.MOBILE);
        checkPhone(HOME_PHONE, phones.get(1), PhoneType.HOME);
        checkPhone(OFFICE_PHONE, phones.get(2), PhoneType.OFFICE);
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
		Client client = registerClientWith3Phones();
		service.findByFamilyName(FAMILY_NAME, 
				c -> c.getPhones().forEach(
						p -> assertNotNull(p.getNumber())));
		service.deleteClient(client); // Required: not a transactional method
	}
	
	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void registerAndDeleteClient() {
		Client client = registerClientWith3Phones();
		service.deleteClient(client);
		assertEquals(0L, dao.count());
	}
}
