package com.es.api.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {

	private String userName;
	private String oldPassword;
	private String newPassword;

}
