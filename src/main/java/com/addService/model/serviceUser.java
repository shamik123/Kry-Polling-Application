package com.addService.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="serviceUser")
public class serviceUser {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id",unique=true,nullable = false)
	private Long id;
	

	@NotBlank(message = "Service url cannot be null")
	@Column
	private String url;

	

	@NotBlank(message = "User ID cannot be null")
	@Column(name="user_id")
	private String userId;
	
	@Column(name ="status")
	String status;
	
	
	@NotBlank(message = "Service Name cannot be null")
	@Column(unique=true)
	String service;


	public String getService() {
		return service;
	}


	public void setService(String service) {
		this.service = service;
	}


	public String isStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	

}
