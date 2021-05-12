package com.project.lms.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserCreateUpdateDto {
	
	@NotBlank(message = "First Name is mandatory!")
	private String firstName;
	
	@NotBlank(message = "Last Name is mandatory!")
	private String lastName;
	
	@NotBlank(message = "Email is mandatory!")
	private String email;
	
	@NotBlank(message = "Username is mandatory!")
	private String username;
	
	@NotBlank(message = "Password is mandatory!")
	private String password;

}
