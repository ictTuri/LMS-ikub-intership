package com.project.lms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lms.converter.RoleConverter;
import com.project.lms.dto.RoleCreateUpdateDto;
import com.project.lms.dto.RoleDto;
import com.project.lms.model.RoleEntity;
import com.project.lms.repository.RoleRepository;
import com.project.lms.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public List<RoleDto> showAllRoles() {
		
		return RoleConverter.toListDto(roleRepo.getAllRoles());
	}

	@Override
	public RoleDto createNewRole(RoleCreateUpdateDto role) {
		RoleEntity roleToSave = RoleConverter.toEntity(role);
		roleRepo.saveRole(roleToSave);
		return RoleConverter.toDto(roleToSave);
	}

	
}
