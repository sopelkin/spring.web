package edu.sibinfo.spring.web.module05.dto.conversion;

import org.mapstruct.Mapper;

import edu.sibinfo.spring.web.module05.domain.Phone;
import edu.sibinfo.spring.web.module05.dto.PhoneDTO;

@Mapper(componentModel="spring")
public abstract class PhoneDtoConverter extends BaseDtoConverter<Phone, PhoneDTO> {
	public abstract PhoneDTO convert(Phone phone);
}
