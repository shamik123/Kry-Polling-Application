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
	
	public static ServiceStatusService srvService;
	
	@Autowired
    public void setServiceStuatService(ServiceStatusService srvServ) {
        ServiceStatusUtilities.srvService = srvServ;
    }
	
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
		
		try {
		ResponseEntity<String> response
			  = restTemplate.getForEntity(url, String.class);
		System.out.println("Health response" + response);	
		urlStatus = mapper.readValue(response.getBody(), ServiceStatus.class);
		System.out.println("Response after parsing is" + urlStatus.getStatus());
		}
		catch (Exception e) {
			System.out.println("Got exception" + e);
		}
		
		try {
		
	    System.out.println(srvStatus.getService());
	    System.out.println(srvStatus.getUserId());
	    System.out.println(srvStatus.getSrvUrl());
	    System.out.println(urlStatus.getStatus());
	    System.out.println(srvStatus.getUserId());
	    System.out.println(srvStatus.getId());
	    System.out.println("srvService class is " + srvService);
		srvService.updateByServiceStatusId(srvStatus.getService(), srvStatus.getUserId(), srvStatus.getSrvUrl(), urlStatus.getStatus() ,srvStatus.getId());
		}
		catch (Exception e) {
			System.out.println("Exception is " + e);
		}
	
	}
	
	@Scheduled(fixedRate = 10000)
	public void updateServiceStatus() {
		
		String urlModifier = "/actuator/health";
		ServiceStatus sts = null;
		
		List<ServiceStatus> srvList = new ArrayList<ServiceStatus>();
        RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		
		srvList = srvService.getAllServices();
		ResponseEntity<String> response = null;
		
		
		for (ServiceStatus srvStat : srvList) {
			String url = srvStat.getSrvUrl() + urlModifier;
			
			try {
		    response
			  = restTemplate.getForEntity(url, String.class);
			System.out.println("Health response" + response);
			sts = mapper.readValue(response.getBody(), ServiceStatus.class);
			System.out.println("Pre Response after parsing is" + sts.getStatus());
			
			System.out.println("Pre Service is" + srvStat.getService());
			System.out.println("Pre User Id is" + srvStat.getUserId());
			System.out.println("Pre Url is" + srvStat.getSrvUrl());
			System.out.println("Pre status is" + srvStat.getStatus());
			System.out.println("Pre Id is" + srvStat.getId());
			srvService.updateByServiceStatusId(srvStat.getService(), srvStat.getUserId(), srvStat.getSrvUrl(), sts.getStatus(), srvStat.getId());
			}
			catch (Exception e) {
				e.printStackTrace();
				response = null;
			}
			finally {
				System.out.println("Post Response after parsing is" + srvStat.getStatus());
		
				System.out.println("Post Service is" + srvStat.getService());
				System.out.println("Post User Id is" + srvStat.getUserId());
				System.out.println("Post Url is" + srvStat.getSrvUrl());
				System.out.println("Post Id is" + srvStat.getId());
				if (response == null) {
					System.out.println("Post status is" + "DOWN");
					
					System.out.println("Service is" + srvStat.getService());
					System.out.println("user Id is" + srvStat.getUserId());
					System.out.println("url is" + srvStat.getSrvUrl());
					System.out.println("Id is" + srvStat.getId());
					srvService.updateByServiceStatusId(srvStat.getService(), srvStat.getUserId(), srvStat.getSrvUrl(), "DOWN", srvStat.getId());
				}
				else {
				System.out.println("Post Service is" + srvStat.getService());
				System.out.println("Post User Id is" + srvStat.getUserId());
				System.out.println("Post Url is" + srvStat.getSrvUrl());
				System.out.println("Response is" + response);
				System.out.println("Post Id is" + srvStat.getId());
				System.out.println("Post status is" + sts.getStatus());
					srvService.updateByServiceStatusId(srvStat.getService(), srvStat.getUserId(), srvStat.getSrvUrl(), sts.getStatus(), srvStat.getId());
				
				}
				
			}
			
  
		    
		}
		
		
		
	}

}
