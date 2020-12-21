package com.addService.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="ServiceStatus")
public class ServiceStatus {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	private String service;
	
	private String userId;
	

	@JsonProperty("status")
	private String status;
	
	private String srvUrl;
	
	public void ServiceStatus(String srv, String url) {
		this.service = srv;
		this.srvUrl = url;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSrvUrl() {
		return srvUrl;
	}
	public void setSrvUrl(String srvUrl) {
		this.srvUrl = srvUrl;
	}

	
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
