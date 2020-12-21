package com.addService.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.addService.Services.ServiceStatusService;
import com.addService.model.ServiceStatus;

@RestController
@RequestMapping("ServiceStatus")
public class ServiceStatusController {
	
	@Autowired
	private ServiceStatusService srvService;
	
	
	@GetMapping(value = "/Service")
	public ResponseEntity<List<ServiceStatus>> getAllServices() {
		return new ResponseEntity<>(srvService.getAllServices(),HttpStatus.OK);
	}
	
	@GetMapping(value = "/Service/Id/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<ServiceStatus>> getByUserId(@Valid @PathVariable String UserId) {

        
    	List<ServiceStatus>  srv = srvService.getAllServiceByUserId(UserId);
    	return new ResponseEntity<>(srv, HttpStatus.OK);
		
    }
	

}
