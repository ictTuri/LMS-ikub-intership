package com.project.lms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LoginLogoutController {
	
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(){
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
