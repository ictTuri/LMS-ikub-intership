package com.project.lms.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserRegisterDto {
	
	@NotBlank(message = "First Name is mandatory!")
	private String firstName;
	
	@NotBlank(message = "Last Name is mandatory!")
	private String lastName;
	
	@Email(message = "Please enter a valid email!")
	private String email;
	
	@NotBlank(message = "Username is mandatory!")
	private String username;
	
	@NotBlank(message = "Password is mandatory!")
	private String password;

}
