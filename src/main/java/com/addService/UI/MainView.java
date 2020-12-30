package com.addService.UI;



import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.addService.Repository.*;
import com.addService.Services.ServiceStatusService;
import com.addService.Services.serviceUserService;
import com.addService.model.*;
import com.addService.utilities.ServiceStatusUtilities;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;

import java.util.Collection;
import java.util.Locale;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import org.springframework.beans.factory.annotation.Autowired;

import com.addService.Repository.*;
import com.addService.model.*;

@Route(value="TestApp")
public class MainView extends VerticalLayout {

	   private final serviceStatusRepository srvStatRepository;
	   private final serviceUserRepository srvUserRepository;
	   
       private Grid<ServiceStatus> ServiceStatusGrid = new Grid<>(ServiceStatus.class);
       private List<ServiceStatus> serviceStatus = new LinkedList<>();

	   public MainView(serviceStatusRepository srvRepo, serviceUserRepository usrRepo) {
	        this.srvStatRepository = srvRepo;
	        this.srvUserRepository = usrRepo; 
	        
	        add(
	                new H1("Add new Service"),
	                buildForm(),
	                ServiceStatusGrid);
	        

	    }
         
	    private Component buildForm() {
	    	
	    	TextField nameField = new TextField("Service Name");
	        TextField urlField = new TextField("Service url");
	        TextField userField = new TextField("user Id");
	    
	        Button orderButton = new Button("Add Service");
	        Div errorsLayout = new Div();

	        orderButton.setThemeName("primary");

	        HorizontalLayout formLayout = new HorizontalLayout(nameField, urlField, userField, orderButton);
	        Div wrapperLayout = new Div(formLayout, errorsLayout);
	        formLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
	        wrapperLayout.setWidth("100%");
	        
	        Binder<serviceUser> binder = new Binder<>(serviceUser.class);
		        binder.forField(nameField)
		            .asRequired("Name is required")
		            .withValidator(name -> serviceNameValidation(name), "Service Name exists")
		            .bind(serviceUser::getService, serviceUser::setService);
		        binder.forField(urlField)
	            .asRequired("url is required")
	            .bind(serviceUser::getUrl, serviceUser::setUrl);
		        binder.forField(userField)
	            .asRequired("User Id is required")
	            .bind(serviceUser::getUserId, serviceUser::setUserId);
		        binder.readBean(new serviceUser());
		        
		        initializeServiceStatusGrid();
		        
		        binder.addStatusChangeListener(status -> {
		            
		            boolean emptyFields = Stream.of("service", "srvUrl", "userId")
		                .flatMap(prop -> binder.getBinding(prop).stream())
		                .anyMatch(binding -> binding.getField().isEmpty());
		            orderButton.setEnabled(!status.hasValidationErrors() && !emptyFields);
		          }
		      );
		        
		     
		        orderButton.addClickListener(click -> {
	         	  try {
		        		
		        	    errorsLayout.setText(""); 

		        	   
		        	    serviceUser newService = new serviceUser();
		        	    binder.writeBean(newService); 
		        	   
		        	    addService(newService); 
		        	    binder.readBean(new serviceUser());
		        	  }
		       	   catch (ValidationException e) {
		        		
		        	    errorsLayout.add(new Html(e.getValidationErrors().stream()
		        	        .map(res -> "<p>" + res.getErrorMessage() + "</p>")
		        	        .collect(Collectors.joining("\n")))); 
		        	  } 
		        	});
		        
		        
	
		        return wrapperLayout;
		    	
		    }
	        
		    private void addService(serviceUser resource) {
		    	
		    	
		    	serviceUser urlDTO = new serviceUser(); 
				urlDTO.setUrl(resource.getUrl());
				urlDTO.setUserId(resource.getUserId());
				urlDTO.setService(resource.getService());
				
				ServiceStatus srvStat = new ServiceStatus();
				srvStat.setUserId(resource.getUserId());
				srvStat.setService(resource.getService());
				srvStat.setSrvUrl(resource.getUrl());
				srvStat.setStatus("DOWN");
				
				srvStatRepository.save(srvStat);
				ServiceStatusUtilities.getStatus(srvStat);
		    	srvUserRepository.save(urlDTO);
		    	  
		    	
		    	initializeServiceStatusGrid();
		    }
		    
		    private void initializeServiceStatusGrid() {
		    	
		        List<ServiceStatus> serviceStatusList = new LinkedList<>();
		    	srvStatRepository.findAll().forEach(serviceStatusList::add);
		    	
		    	ServiceStatusGrid.setItems(serviceStatusList);
		    }
		    
		    private boolean serviceNameValidation(String user) {
		    	
		    	serviceUser srvUser = null;
		    	srvUser = srvUserRepository.findByServiceName(user);
		    	if (srvUser != null)
		    	  return false;
		    	else
		    	 return true;
		    }
		    
		    

	}

