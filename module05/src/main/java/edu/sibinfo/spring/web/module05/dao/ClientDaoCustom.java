package edu.sibinfo.spring.web.module05.dao;

import edu.sibinfo.spring.web.module05.domain.Client;

public interface ClientDaoCustom {
	Iterable<Client> search(String charactersitics);
}
