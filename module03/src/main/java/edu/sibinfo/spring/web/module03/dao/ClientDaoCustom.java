package edu.sibinfo.spring.web.module03.dao;

import edu.sibinfo.spring.web.module03.domain.Client;

public interface ClientDaoCustom {
	Iterable<Client> search(String charactersitics);
}
