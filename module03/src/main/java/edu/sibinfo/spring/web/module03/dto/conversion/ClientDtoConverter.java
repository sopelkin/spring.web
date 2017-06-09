package edu.sibinfo.spring.web.module03.dto.conversion;

import java.util.List;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Component;

import edu.sibinfo.spring.web.module03.domain.Client;
import edu.sibinfo.spring.web.module03.domain.Phone;
import edu.sibinfo.spring.web.module03.dto.ClientDTO;
import edu.sibinfo.spring.web.module03.dto.PhoneDTO;

@Component
public class ClientDtoConverter extends BaseDtoConverter<Client, ClientDTO> {

	private static final TypeDescriptor TD_PHONES = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(Phone.class));
	private static final TypeDescriptor TD_DTOS = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(PhoneDTO.class));

	@Override
	public ClientDTO convert(Client client) {
		ClientDTO dto = new ClientDTO();
		dto.setFamilyName(client.getFamilyName());
		dto.setFirstName(client.getFirstName());
		dto.setId(client.getId());
		@SuppressWarnings("unchecked")
		Iterable<PhoneDTO> phones = (Iterable<PhoneDTO>) service.convert(
				client.getPhones(), TD_PHONES, TD_DTOS); 
		dto.setPhones(phones);
		return dto;
	}
}
