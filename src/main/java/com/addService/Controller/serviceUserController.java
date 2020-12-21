package com.addService.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.addService.Exceptions.DuplicateRequestException;
import com.addService.Services.*;
import com.addService.model.*;
import com.addService.utilities.ServiceStatusUtilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;



@RestController
@RequestMapping("urlService")
public class serviceUserController {
	
	@Autowired
	private serviceUserService urlService;
	
	@Autowired
	private ServiceStatusService srvService;
	
	
	@GetMapping(value = "/Service")
	public ResponseEntity<List<serviceUser>> getAllServices() {
		return new ResponseEntity<>(urlService.getAllServices(),HttpStatus.OK);
	}
	
	@PostMapping(value = "/Service")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<serviceUser> create(@Valid @RequestBody serviceUser resource) throws DuplicateRequestException {
    	
    	serviceUser extUser = null;
    	
    	extUser = urlService.findByServiceName(resource.getService());
    	
    	
    	if (extUser!=null)
    		throw new DuplicateRequestException();
    
        
		serviceUser urlDTO = new serviceUser(); 
		urlDTO.setUrl(resource.getUrl());
		urlDTO.setUserId(resource.getUserId());
		urlDTO.setService(resource.getService());
		
		ServiceStatus srvStat = new ServiceStatus();
		srvStat.setUserId(resource.getUserId());
		srvStat.setService(urlDTO.getService());
		srvStat.setSrvUrl(resource.getUrl());
		srvStat.setStatus("DOWN");
		srvService.create(srvStat);
		
		ServiceStatusUtilities.getStatus(srvStat);
		
		
        return new ResponseEntity<>(urlService.create(urlDTO),HttpStatus.OK);
    }
	
	@DeleteMapping(value = "/Service/Id/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> deleteByUserId(@Valid @PathVariable String UserId) {
    	
    	
    
        
    	urlService.deleteByUsrId(UserId);
    	srvService.deleteByUsrId(UserId);
    	return new ResponseEntity<>(UserId, HttpStatus.OK);
		
    }
	
	
	@DeleteMapping(value = "/Service/{ServiceName}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> deleteByServiceName(@Valid @PathVariable String srvName) {
    	
    	
    
        urlService.deleteByServicename(srvName);
        srvService.deleteByServicename(srvName);
    
    	return new ResponseEntity<>(srvName, HttpStatus.OK);
		
    }
	
	
	
}
