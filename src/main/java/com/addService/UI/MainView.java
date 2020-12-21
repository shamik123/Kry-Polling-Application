package com.addService.UI;



import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.addService.Repository.*;
import com.addService.Services.ServiceStatusService;
import com.addService.model.*;


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

	 

	    final Grid<ServiceStatus> grid;

	   

	    public MainView(serviceStatusRepository repo) {
	        this.srvStatRepository = repo;
	     
	        this.grid = new Grid<>(ServiceStatus.class);
	        add(grid);
	        listStatus();  
	        

	    }
	    
	    private void listStatus() {
	    	grid.setItems((Collection<ServiceStatus>) srvStatRepository.findAll()); 
	    }

}

