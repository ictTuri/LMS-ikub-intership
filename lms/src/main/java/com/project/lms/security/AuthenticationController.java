package com.project.lms.security;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
	
	private final JwtService jwtProvider;

	private final AuthenticationManager authenticationManager;

	@Autowired
	public AuthenticationController(JwtService jwtProvider, AuthenticationManager authenticationManager) {
		this.jwtProvider = jwtProvider;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/api/login")
	public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginRequest request) {
		Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(request.getUsername(),
				request.getPassword());
		Authentication authenticationResult = authenticationManager.authenticate(authenticationRequest);
		LoginResponse response = createResponse(authenticationResult);
		return ResponseEntity.ok(response);
	}

	private LoginResponse createResponse(Authentication authentication) {
		UserPrincipal agilityUserDetail = (UserPrincipal) authentication.getPrincipal();
		LoginResponse response = new LoginResponse();
		List<String>  roles= agilityUserDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		String generatedToken = jwtProvider.issueToken(agilityUserDetail);
		response.setRole(roles);
		response.setUsername(agilityUserDetail.getUsername());
		response.setToken(generatedToken);
		return response;
	}
}
