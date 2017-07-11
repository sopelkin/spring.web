package edu.sibinfo.spring.web.module05.service.impl;

import edu.sibinfo.spring.web.module05.dao.ClientDao;
import edu.sibinfo.spring.web.module05.dao.PhoneType;
import edu.sibinfo.spring.web.module05.domain.Client;
import edu.sibinfo.spring.web.module05.domain.Phone;
import edu.sibinfo.spring.web.module05.dto.ClientDTO;
import edu.sibinfo.spring.web.module05.dto.PhoneDTO;
import edu.sibinfo.spring.web.module05.service.ClientRegisteredEvent;
import edu.sibinfo.spring.web.module05.service.ClientService;
import edu.sibinfo.spring.web.module05.service.ClientServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.function.Consumer;

@Service
public class ClientServiceImpl implements ClientService {

  private static final TypeDescriptor LIST_OF_CLIENT = TypeDescriptor.collection(List.class,
      TypeDescriptor.valueOf(Client.class));
  private static final TypeDescriptor LIST_OF_DTO = TypeDescriptor.collection(List.class,
      TypeDescriptor.valueOf(ClientDTO.class));
  private final ClientDao clientDao;
  private final MessageDigest encoder;
  private final ApplicationEventPublisher publisher;
  private final ConversionService conversionService;
  @Autowired
  private ClientService clientService;

  @Autowired
  public ClientServiceImpl(ClientDao clientDao, MessageDigest encoder, ConversionService conversionService,
                           ApplicationEventPublisher publisher) {
    super();
    this.clientDao = clientDao;
    this.encoder = encoder;
    this.publisher = publisher;
    this.conversionService = conversionService;
  }

  @Override
  public ClientDTO register(String firstName, String familyName, String mobile) {
    Client client = new Client(familyName, firstName);
    Phone phone = new Phone(mobile, PhoneType.MOBILE);
    client.addPhone(phone);
    clientDao.save(client);
    ClientDTO result = convert(client);
    publisher.publishEvent(new ClientRegisteredEvent(result, convert(phone)));
    return result;
  }

  @Override
  @Transactional
  public ClientDTO addPhone(ClientDTO clientDTO, String number, PhoneType phoneType) {
    Phone phone = new Phone(number, phoneType);
    Client client = findClient(clientDTO.getId());
    client.addPhone(phone);
    clientDao.save(client);
    return conversionService.convert(client, ClientDTO.class);
  }

  @Override
  @Transactional
  public void setPassword(ClientDTO clientDTO, String password) {
    Client client = findClient(clientDTO.getId());
    client.setPasswordEncoded(encoder.digest(password.getBytes(StandardCharsets.UTF_8)));
    clientDao.save(client);
  }

  @Override
  public void deleteClient(ClientDTO client) {
    clientDao.delete(client.getId()); // clientDao.delete(client) fails in non-transactional context
  }

  @Override
  public ClientDTO findByPhone(String number) {
    return convert(clientDao.findByPhone(number));
  }

  @Override
  public ClientDTO findByFamilyName(String familyName) {
    return convert(clientDao.findByFamilyName(familyName));
  }

  @Transactional
  @Override
  public ClientDTO findByFamilyName(String familyName, Consumer<Client> consumer) {
    Client c = clientDao.findByFamilyName(familyName);
    consumer.accept(c);
    return convert(c);
  }

  @Override
  public Iterable<ClientDTO> search(String charactersitics) {
    Iterable<Client> clients = clientDao.search(charactersitics);
    return convert(clients);
  }

  @Override
  public Page<ClientDTO> findAll(int page) {
    PageRequest request = new PageRequest(page, 10, new Sort("familyName", "firstName"));
    Page<Client> domainPage = clientDao.findAll(request);
    return new PageImpl<>(clientService.convert(domainPage.getContent()), request, domainPage.getTotalElements());
  }

  @Override
  public ClientDTO findOne(long clientId) {
    return convert(findClient(clientId));
  }

  @Override
  @Transactional
  public ClientDTO update(ClientDTO dto) {
    Client client = findClient(dto.getId());
    client.setFamilyName(dto.getLastName());
    client.setFirstName(dto.getName());
    return conversionService.convert(client, ClientDTO.class);
  }

  @Override
  public List<ClientDTO> convert(Iterable<Client> clients) {
    @SuppressWarnings("unchecked")
    List<ClientDTO> result = (List<ClientDTO>) conversionService.convert(clients, LIST_OF_CLIENT, LIST_OF_DTO);
    return result;
  }

  private ClientDTO convert(Client client) {
    return conversionService.convert(client, ClientDTO.class);
  }

  private PhoneDTO convert(Phone phone) {
    return conversionService.convert(phone, PhoneDTO.class);
  }

  private Client findClient(long clientId) {
    Client client = clientDao.findOne(clientId);
    if (client == null)
      throw new ClientServiceException(1, "Client doesn't exist: ID=" + clientId);
    return client;
  }
}
