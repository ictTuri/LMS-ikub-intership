package com.project.lms.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDto {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private List<String> roles;
	private boolean activated;
}
