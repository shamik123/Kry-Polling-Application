package com.addService.utilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.addService.Services.ServiceStatusService;
import com.addService.model.ServiceStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ServiceStatusUtilities {
	
	@Autowired
	ServiceStatusService srvService;
	
	@Async("threadPoolTaskExecutor")
	public static void getStatus(ServiceStatus srvStatus) {
	    
		RestTemplate restTemplate = new RestTemplate();
		
		ObjectMapper mapper = new ObjectMapper();
		ServiceStatus urlStatus = null;
		String status = "DOWN";
		String baseUrl = srvStatus.getSrvUrl();
		
		String urlModifier
		  = "/actuator/health";
		String url = baseUrl + urlModifier;
		System.out.println("Url to be pinged is " + url);
		ResponseEntity<String> response
		  = restTemplate.getForEntity(url, String.class);
		System.out.println("Health response" + response);
		try {
		urlStatus = mapper.readValue(response.getBody(), ServiceStatus.class);
		System.out.println("Response after parsing is" + urlStatus.getStatus());
		}
		catch (Exception e) {
			System.out.println("Got exception" + e);
		}
		
	    srvStatus.setStatus(urlStatus.getStatus());
	    
	
	}
	
	@Scheduled(fixedRate = 10000)
	public void updateServiceStatus() {
		
		String urlModifier = "/actuator/health";
		
		List<ServiceStatus> srvList = new ArrayList<ServiceStatus>();
        RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		
		srvList = srvService.getAllServices();
		
		for (ServiceStatus srvStat : srvList) {
			String url = srvStat.getSrvUrl() + urlModifier;
			ResponseEntity<String> response
			  = restTemplate.getForEntity(url, String.class);
			System.out.println("Health response" + response);
			try {
			srvStat = mapper.readValue(response.getBody(), ServiceStatus.class);
			System.out.println("Response after parsing is" + srvStat.getStatus());
			}
			catch (Exception e) {
				System.out.println("Got exception" + e);
			}
			
		    
		}
		
	}

}
