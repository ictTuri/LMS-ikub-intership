package com.project.lms.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("api/v1")
public class LoginLogoutController {
	
	@PostMapping("_logout")
	public void logoutHere(){
		// Trigers logout
	}
}
