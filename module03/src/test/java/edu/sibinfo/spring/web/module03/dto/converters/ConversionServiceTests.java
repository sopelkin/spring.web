package edu.sibinfo.spring.web.module03.dto.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;

import edu.sibinfo.spring.web.module03.dao.PhoneType;
import edu.sibinfo.spring.web.module03.domain.Client;
import edu.sibinfo.spring.web.module03.domain.Phone;
import edu.sibinfo.spring.web.module03.dto.ClientDTO;
import edu.sibinfo.spring.web.module03.dto.PhoneDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConversionServiceTests {

    private static final String FAMILY_NAME = "LastName";
    private static final String FIRST_NAME = "FirstName";
	private static final String MOBILE_PHONE = "+701010101";
	private static final String HOME_PHONE = "+702020202";
	private static final String OFFICE_PHONE = "+702020202";
	
	@Autowired
	private ConversionService conversionService;
	
	@Test
	public void convertPhoneDTO() {
		assertTrue(conversionService.canConvert(Phone.class, PhoneDTO.class));
		
		Phone phone = new Phone(MOBILE_PHONE, PhoneType.MOBILE);
		PhoneDTO dto = conversionService.convert(phone, PhoneDTO.class);
		assertPhone(dto, MOBILE_PHONE, PhoneType.MOBILE);
	}

	@Test
	public void convertClientDTO() {
		assertTrue(conversionService.canConvert(Client.class, ClientDTO.class));
		
		Client client = new Client(FAMILY_NAME, FIRST_NAME);
		client.addPhone(new Phone(HOME_PHONE, PhoneType.HOME));
		client.addPhone(new Phone(MOBILE_PHONE, PhoneType.MOBILE));
		client.addPhone(new Phone(OFFICE_PHONE, PhoneType.OFFICE));
		
		ClientDTO dto = conversionService.convert(client, ClientDTO.class);
		
		assertNotNull(dto);
		assertEquals(FAMILY_NAME, dto.getFamilyName());
		assertEquals(FIRST_NAME, dto.getFirstName());
		
		List<PhoneDTO> list = new ArrayList<PhoneDTO>(3);
		Iterator<PhoneDTO> iterator = dto.getPhones().iterator();
		while (iterator.hasNext())
			list.add(iterator.next());
		assertEquals(3, list.size());
		assertPhone(list.get(0), HOME_PHONE, PhoneType.HOME);
		assertPhone(list.get(1), MOBILE_PHONE, PhoneType.MOBILE);
		assertPhone(list.get(2), OFFICE_PHONE, PhoneType.OFFICE);
	}
	
	private void assertPhone(PhoneDTO dto, String phoneNumber, PhoneType phoneType) {
		assertNotNull(dto);
		assertEquals(phoneNumber, dto.getNumber());
		assertEquals(phoneType.name(), dto.getPhoneType());
	}
}
