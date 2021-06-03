package com.project.lms.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.exception.InvalidCredentialsException;
import com.project.lms.jwt.TokenProvider;
import com.project.lms.jwt.UsernameAndPasswordAuthenticationRequest;
@RestController
@RequestMapping("api/v1")
public class LoginLogoutController {
	
	private static final String USERNOTAUTHENTICATED = "Unable to Authenticate! Check username and password!";

	private final AuthenticationManager authenticationManager;
	private final TokenProvider tokenProvider;
	
	public LoginLogoutController(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
		super();
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
	}

	@PostMapping("/_login")
	public ResponseEntity<String> login(HttpServletResponse response,
			@Valid @RequestBody UsernameAndPasswordAuthenticationRequest credentials){
		 
		try {
			var authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
			String token = tokenProvider.createToken(authenticate);
			var cookie = tokenProvider.createCookie(token);
			response.addCookie(cookie);
		}catch(AuthenticationException e) {
			throw new InvalidCredentialsException(USERNOTAUTHENTICATED);
		}
		return new ResponseEntity<>("Login successfully ",HttpStatus.OK);
	}
	
	@PostMapping("/_logout")
	public void logout(){
		// Trigers logout
	}
}
