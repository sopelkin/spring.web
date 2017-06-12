package edu.sibinfo.spring.web.module04.dao;

import edu.sibinfo.spring.web.module04.domain.Client;

public interface ClientDaoCustom {
	Iterable<Client> search(String charactersitics);
}
