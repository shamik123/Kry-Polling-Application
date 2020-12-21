package com.addService.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.addService.model.ServiceStatus;
import com.addService.model.serviceUser;

public interface serviceStatusRepository extends CrudRepository<ServiceStatus,String> {
	
	@Query("SELECT u FROM ServiceStatus u WHERE u.userId = ?1")
	ServiceStatus findByUserId(String userId);
	
	
	@Query("SELECT u FROM ServiceStatus u WHERE u.service = ?1")
	ServiceStatus findByServiceName(String service);
	

}
