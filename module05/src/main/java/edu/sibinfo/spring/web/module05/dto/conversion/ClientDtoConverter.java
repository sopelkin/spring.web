package edu.sibinfo.spring.web.module05.dto.conversion;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import edu.sibinfo.spring.web.module05.domain.Client;
import edu.sibinfo.spring.web.module05.domain.Phone;
import edu.sibinfo.spring.web.module05.dto.ClientDTO;
import edu.sibinfo.spring.web.module05.dto.PhoneDTO;

@Mapper(componentModel="spring", imports = PhoneDtoConverter.class)
public abstract class ClientDtoConverter extends BaseDtoConverter<Client, ClientDTO> {
	
	@Mapping(source="familyName", target="lastName")
	@Mapping(source="firstName", target="name")
	public abstract ClientDTO convert(Client client);
	
	public PhoneDTO convert(Phone phone) {
		return service.convert(phone, PhoneDTO.class);
	}
}
