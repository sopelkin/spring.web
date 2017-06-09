package edu.sibinfo.spring.web.module03.dto.conversion;

import org.springframework.stereotype.Component;

import edu.sibinfo.spring.web.module03.domain.Phone;
import edu.sibinfo.spring.web.module03.dto.PhoneDTO;

@Component
public class PhoneDtoConverter extends BaseDtoConverter<Phone, PhoneDTO> {

	@Override
	public PhoneDTO convert(Phone phone) {
		return new PhoneDTO(phone.getNumber(), phone.getPhoneType().name());
	}
}
