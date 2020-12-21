package com.addService.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addService.Repository.*;
import com.addService.model.*;

@Service
public class serviceUserService {
	
	@Autowired
	private serviceUserRepository urlRepository;
	

	public List getAllServices() {
		List urls = new ArrayList<>();
	    urlRepository.findAll().forEach(urls::add);
		return urls;
	}
	
	public serviceUser create(serviceUser url) {
		return urlRepository.save(url);
		
	}
	
	public serviceUser deleteByUsrId(String userId) {
		
		serviceUser srvUsr = urlRepository.findByUserId(userId);
		urlRepository.delete(srvUsr);
		return srvUsr;
	}
	
	public serviceUser deleteByServicename(String srvName) {
		serviceUser srvUsr = urlRepository.findByServiceName(srvName);
		urlRepository.delete(srvUsr);
		return srvUsr;
	}

	public serviceUser findByServiceName(String srvName) {
		serviceUser srvUsr = urlRepository.findByServiceName(srvName);
		return srvUsr;
	}
}
