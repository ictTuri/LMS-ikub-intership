package com.project.lms.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.dto.CustomResponseDto;
import com.project.lms.dto.UserRegisterDto;
import com.project.lms.service.UserService;

@RestController
@RequestMapping("api/v1/register")
public class RegisterController {

	private UserService userService;
	
	public RegisterController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping()
	public ResponseEntity<CustomResponseDto> registerStudent(@Valid @RequestBody UserRegisterDto user) {
		return new ResponseEntity<>(userService.registerStudent(user),HttpStatus.CREATED);
	}
}
