package com.project.lms.security;

import java.util.List;

import lombok.Data;

@Data
public class LoginResponse {
	private String username;

	private List<String> role;

	private String token;
}
