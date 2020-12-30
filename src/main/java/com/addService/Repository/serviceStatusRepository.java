package com.addService.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.addService.model.ServiceStatus;
import com.addService.model.serviceUser;

public interface serviceStatusRepository extends CrudRepository<ServiceStatus,String> {
	
	@Query("SELECT u FROM ServiceStatus u WHERE u.userId = ?1")
	ServiceStatus findByUserId(String userId);
	
	
	@Query("SELECT u FROM ServiceStatus u WHERE u.service = ?1")
	ServiceStatus findByServiceName(String service);
	
	@Modifying
	@Query("update ServiceStatus u set u.service = ?1, u.userId = ?2, u.srvUrl = ?3, u.status = ?4 where u.id = ?5")
	void setServiceStatusById(String service, String userId, String url, String status, long id );
	

}
