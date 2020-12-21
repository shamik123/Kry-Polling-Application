package com.addService.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.addService.model.*;

public interface serviceUserRepository extends CrudRepository<serviceUser,String>  {
	
	@Query("SELECT u FROM serviceUser u WHERE u.userId = ?1")
	serviceUser findByUserId(String userId);
	
	@Query("SELECT u FROM serviceUser u WHERE u.service = ?1")
	serviceUser findByServiceName(String service);
	
}
