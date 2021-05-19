package com.project.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.dto.RoleCreateUpdateDto;
import com.project.lms.dto.RoleDto;
import com.project.lms.service.RoleService;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<List<RoleDto>> showAllRoles(){
		return new ResponseEntity<>(roleService.showAllRoles(),HttpStatus.OK);
	}
	
	@PostMapping()
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<RoleDto> createNewRole(@RequestBody RoleCreateUpdateDto role){
		return new ResponseEntity<>(roleService.createNewRole(role),HttpStatus.CREATED);
	}
}
