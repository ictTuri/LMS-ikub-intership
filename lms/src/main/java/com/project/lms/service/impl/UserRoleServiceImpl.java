package com.project.lms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.lms.converter.UserRoleConverter;
import com.project.lms.dto.UserRoleCreateUpdateDto;
import com.project.lms.dto.UserRoleDto;
import com.project.lms.entity.RoleEntity;
import com.project.lms.entity.UserEntity;
import com.project.lms.entity.UserRoleEntity;
import com.project.lms.exception.MyExcMessages;
import com.project.lms.repository.RoleRepository;
import com.project.lms.repository.UserRepository;
import com.project.lms.repository.UserRoleRepository;
import com.project.lms.service.UserRoleService;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

	private UserRoleRepository userRoleRepository;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserRepository userRepository,
			RoleRepository roleRepository) {
		super();
		this.userRoleRepository = userRoleRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public List<UserRoleDto> getAllUserRole() {
		return UserRoleConverter.toDtoList(userRoleRepository.getAllUserRoles());
	}

	@Override
	public UserRoleDto getUserRoleById(Long id) {
		UserRoleEntity userRoleToReturn = userRoleRepository.getUserRoleById(id);
		if (userRoleToReturn != null) {
			return UserRoleConverter.toDto(userRoleToReturn);
		}
		throw new MyExcMessages("User Role Relation with id: " + id + " can not be found");
	}

	@Override
	public UserRoleDto createUserRole(UserRoleCreateUpdateDto userRole) {
		UserEntity userToConnect = userRepository.getUserByUsername(userRole.getUsername());
		if (userToConnect != null) {
			String roleName = userRole.getRole().toUpperCase();
			RoleEntity roleToConnect = roleRepository.getRole(roleName);
			if (roleToConnect != null) {
				boolean foundRole = false;
				List<RoleEntity> roleList = roleRepository.getUserRole(userToConnect);
				for (RoleEntity re : roleList) {
					if (re.getName().equals(roleToConnect.getName())) {
						foundRole = true;
					}
				}
				if (!foundRole) {
					UserRoleEntity userRoleToAdd = new UserRoleEntity();
					userRoleToAdd.setRole(roleToConnect);
					userRoleToAdd.setUser(userToConnect);
					userRoleRepository.saveUserRole(userRoleToAdd);
					return UserRoleConverter.toDto(userRoleToAdd);
				}
				throw new MyExcMessages("Relation between User and Role already exist!");
			}
			throw new MyExcMessages("Given Role is not valid,try Admin or Student or Secretary");
		}
		throw new MyExcMessages("There is no user with username: " + userRole.getUsername());
	}

	@Override
	public UserRoleDto updateUserRole(long id, UserRoleCreateUpdateDto userRole) {
		UserRoleEntity userRoleToUpdate = userRoleRepository.getUserRoleById(id);
		if (userRoleToUpdate != null) {
			UserEntity userToUpdate = userRepository.getUserByUsername(userRole.getUsername());
			if (userToUpdate != null) {
				String roleName = userRole.getRole().toUpperCase();
				RoleEntity roleToUpdate = roleRepository.getRole(roleName);
				if (roleToUpdate != null) {
					List<RoleEntity> roleList = roleRepository.getUserRole(userToUpdate);
					boolean foundRole = isUserRoleConnected(roleList, userRole);
					if (!foundRole) {
						userRoleToUpdate.setRole(roleToUpdate);
						userRoleToUpdate.setUser(userToUpdate);
						return UserRoleConverter.toDto(userRoleRepository.updateUserRole(userRoleToUpdate));
					}
					throw new MyExcMessages("This Relation already exist!");
				}
				throw new MyExcMessages("Role with name: " + id + " not found !");
			}
			throw new MyExcMessages("User Role Relation with id: " + id + " not found!");
		}
		throw new MyExcMessages("User Role Relation with id: " + id + " not found !");
	}

	public boolean isUserRoleConnected(List<RoleEntity> roles, UserRoleCreateUpdateDto user) {
		for (RoleEntity re : roles) {
			if (re.getName().equalsIgnoreCase(user.getRole())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void deleteUserRole(long id) {
		UserRoleEntity userRoleToDelete = userRoleRepository.getUserRoleById(id);
		if (userRoleToDelete != null) {
			int roleListSize = roleRepository.getUserRole(userRoleToDelete.getUser()).size();
			if (roleListSize > 1) {
				userRoleRepository.deleteUserRole(userRoleToDelete);
			}else {
				throw new MyExcMessages("This user has only this role!");
			}
		}else {
			throw new MyExcMessages("User Role Relation with id: " + id + " was not found!");
		}
	}

}
