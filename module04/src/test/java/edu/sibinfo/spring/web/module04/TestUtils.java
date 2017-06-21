package edu.sibinfo.spring.web.module04;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.sibinfo.spring.web.module04.dao.PhoneType;
import edu.sibinfo.spring.web.module04.dto.ClientDTO;
import edu.sibinfo.spring.web.module04.dto.PhoneDTO;

public final class TestUtils {
	// It'a a library class
	private TestUtils() {
		throw new RuntimeException("Instatiation is not supported for: " + getClass().getName());
	}

	public static void assertPhone(PhoneDTO dto, String phoneNumber, PhoneType phoneType) {
		assertNotNull(dto);
		assertEquals(phoneNumber, dto.getNumber());
		assertEquals(phoneType.name(), dto.getPhoneType());
	}

	public static List<PhoneDTO> getPhoneList(ClientDTO dto) {
		List<PhoneDTO> list = new ArrayList<PhoneDTO>(3);
		Iterator<PhoneDTO> iterator = dto.getPhones().iterator();
		while (iterator.hasNext())
			list.add(iterator.next());
		return list;
	}
}
