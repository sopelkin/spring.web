package edu.sibinfo.spring.web.module02.dao;

import edu.sibinfo.spring.web.module02.domain.Client;

public interface ClientDaoCustom {
	Iterable<Client> search(String charactersitics);
}
