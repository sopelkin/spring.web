package edu.sibinfo.spring.web.module04.dto.conversion;

import org.mapstruct.Mapper;

import edu.sibinfo.spring.web.module04.domain.Phone;
import edu.sibinfo.spring.web.module04.dto.PhoneDTO;

@Mapper(componentModel="spring")
public abstract class PhoneDtoConverter extends BaseDtoConverter<Phone, PhoneDTO> {
	public abstract PhoneDTO convert(Phone phone);
}
