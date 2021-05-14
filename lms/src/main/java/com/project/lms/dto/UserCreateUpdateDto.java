package com.project.lms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.project.lms.enums.Roles;

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
	
	@NotBlank(message = "Role is mandatory!")
	private Roles role;
	
	@NotNull(message = "Please provide activated value!")
	private boolean activated;
}
