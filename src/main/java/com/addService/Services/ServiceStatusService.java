package com.addService.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addService.Repository.serviceStatusRepository;
import com.addService.model.ServiceStatus;
import com.addService.model.serviceUser;



@Service
public class ServiceStatusService {
	
	@Autowired
	private serviceStatusRepository srvRepository;
	

	public List getAllServices() {
		List urls = new ArrayList<>();
	    srvRepository.findAll().forEach(urls::add);
		return urls;
	}
	
	public ServiceStatus create(ServiceStatus url) {
		return srvRepository.save(url);
		
	}
	
	public List<ServiceStatus> getAllServiceByUserId(String userId){
		
		List urls = new ArrayList<>();
	    srvRepository.findByUserId(userId);
		return urls;
	}
	
public ServiceStatus deleteByUsrId(String userId) {
		
		ServiceStatus srvSts = srvRepository.findByUserId(userId);
		srvRepository.delete(srvSts);
		return srvSts;
	}
	
	public ServiceStatus deleteByServicename(String srvName) {
		ServiceStatus srvSts = srvRepository.findByServiceName(srvName);
		srvRepository.delete(srvSts);
		return srvSts;
	}
	

}