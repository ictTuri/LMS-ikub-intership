package com.project.lms.security;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {
	
	@NotBlank(message = "Please insert your username !")
	private String username;
	
	@NotBlank(message = "Please insert your password !")
	private String password;
}
