package com.es.api.request;


import lombok.Data;

@Data
public class UserRequest {
	
	private String name;
	private String userName;
	private String password;
	private String role;
	private String branch;
	private String createdBy;

}
