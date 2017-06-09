package edu.sibinfo.spring.web.module03.dto.conversion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import edu.sibinfo.spring.web.module03.domain.Client;
import edu.sibinfo.spring.web.module03.domain.Phone;
import edu.sibinfo.spring.web.module03.dto.ClientDTO;
import edu.sibinfo.spring.web.module03.dto.PhoneDTO;

@Component
public class ClientDtoConverter extends BaseDtoConverter<Client, ClientDTO> {

	@Override
	public ClientDTO convert(Client client) {
		ClientDTO dto = new ClientDTO();
		dto.setFamilyName(client.getFamilyName());
		dto.setFirstName(client.getFirstName());
		dto.setId(client.getId());
		List<PhoneDTO> phones = new ArrayList<PhoneDTO>(client.getPhones().size());
		for (Phone phone : client.getPhones()) {
			phones.add(service.convert(phone, PhoneDTO.class));
		}
		dto.setPhones(phones);
		return dto;
	}
}
