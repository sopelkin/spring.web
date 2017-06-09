package edu.sibinfo.spring.web.module03.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.sibinfo.spring.web.module03.domain.Client;

@Repository
public interface ClientDao extends PagingAndSortingRepository<Client, Long>, ClientDaoCustom
{
	@Query(nativeQuery=true, value="SELECT c.* from client c JOIN phone p ON c.id = p.client_id AND p.phone_number = ?1")
	Client findByPhone(String number);
	
	Client findByFamilyName(String familyName);
}
