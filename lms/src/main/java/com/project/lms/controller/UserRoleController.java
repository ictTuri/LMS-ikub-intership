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

import com.project.lms.dto.UserRoleCreateUpdateDto;
import com.project.lms.dto.UserRoleDto;
import com.project.lms.service.UserRoleService;

@RestController
@RequestMapping("api/v1/user-roles")
public class UserRoleController {
	
	private static final Logger logger = LogManager.getLogger(UserRoleController.class);
	
	private UserRoleService userRoleService;

	public UserRoleController(UserRoleService userRoleService) {
		super();
		this.userRoleService = userRoleService;
	}
	
	@GetMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<UserRoleDto>> showUserRolesData(){
		return new ResponseEntity<>(userRoleService.getAllUserRole(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserRoleDto> showUserRoleById(@PathVariable("id") Long id){
		logger.info("Viewing user roles with id: {}",id);
		return new ResponseEntity<>(userRoleService.getUserRoleById(id),HttpStatus.OK);
	}
	
	@PostMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserRoleDto> createUserRole(@Valid @RequestBody UserRoleCreateUpdateDto userRole){
		logger.info("Creating new user role: {}",userRole);
		return new ResponseEntity<>(userRoleService.createUserRole(userRole),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserRoleDto> updateUserRole(@PathVariable("id") long id ,@Valid @RequestBody UserRoleCreateUpdateDto userRole){
		logger.info("Updating user role relation with id: {} and body: {}",id, userRole);
		return new ResponseEntity<>(userRoleService.updateUserRole(id,userRole),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> softDeleteUser(@PathVariable("id") long id){
		logger.info("Deleting user role relation with id: {}",id);
		userRoleService.deleteUserRole(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
