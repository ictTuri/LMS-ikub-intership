package com.project.lms.jwt;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsernameAndPasswordAuthenticationRequest {
	
	@NotBlank(message = "Please enter a username")
	private String username;
	
	@NotBlank(message = "Please enter a password")
	private String password;
}
