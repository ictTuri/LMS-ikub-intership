package com.project.lms.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.dto.UserCreateUpdateDto;
import com.project.lms.dto.UserDto;
import com.project.lms.service.UserService;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<UserDto>> showAllUsers(){
		return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDto> showUserById(@PathVariable("id") Long id){
		logger.info("Viewing user with id: {}",id);
		return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
	}
	
	@PostMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateUpdateDto user){
		logger.info("Creating new user: {}",user);
		return new ResponseEntity<>(userService.createUser(user),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDto> updateUser(@PathVariable("id") long id, @Valid @RequestBody UserCreateUpdateDto user){
		logger.info("Updating user with id: {} and body: {}",id,user);
		return new ResponseEntity<>(userService.updateUser(id,user),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> softDeleteUser(@PathVariable("id") long id){
		logger.info("Soft Deleting user with id: {}",id);
		userService.softDeleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{id}/hardDelete")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> hardDeleteUser(@PathVariable("id") long id){
		logger.info("Hard Deleting user with id: {}",id);
		userService.hardDeleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
