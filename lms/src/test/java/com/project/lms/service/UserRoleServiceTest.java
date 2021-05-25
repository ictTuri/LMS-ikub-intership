package com.project.lms.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.dto.UserRoleCreateUpdateDto;
import com.project.lms.dto.UserRoleDto;
import com.project.lms.exception.CustomExceptionMessage;
import com.project.lms.exception.ObjectFilteredNotFound;
import com.project.lms.exception.ObjectIdNotFound;
import com.project.lms.model.RoleEntity;
import com.project.lms.model.UserEntity;
import com.project.lms.model.UserRoleEntity;
import com.project.lms.repository.RoleRepository;
import com.project.lms.repository.UserRepository;
import com.project.lms.repository.UserRoleRepository;
import com.project.lms.service.impl.UserRoleServiceImpl;
import com.project.lms.utils.RoleUtil;
import com.project.lms.utils.UserUtil;

@SpringBootTest
class UserRoleServiceTest {


	@InjectMocks
	private UserRoleServiceImpl userRoleService;
	
	@Mock
	private UserRoleRepository userRoleRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	@Test
	void givenUserRoleList_whenGetThem_validateSize() {
		List<UserRoleEntity> userRoles = new ArrayList<>();
		UserRoleEntity userRoleOne = new UserRoleEntity();
		userRoleOne.setRole(RoleUtil.adminRole());
		userRoleOne.setUser(UserUtil.userAdmin());
		userRoles.add(userRoleOne);

		when(userRoleRepository.getAllUserRoles()).thenReturn(userRoles);
		int size = userRoleService.getAllUserRole().size();
		
		assertEquals(1, size);
		verify(userRoleRepository).getAllUserRoles();
	}
	
	@Test
	void givenUserRole_whenUpdate_thenThrowsException() {
		UserRoleCreateUpdateDto userRoleToUpdate = new UserRoleCreateUpdateDto();
		userRoleToUpdate.setRole("ADMIN");
		userRoleToUpdate.setUsername("admin");
		
		UserRoleEntity userRoleOne = new UserRoleEntity();
		userRoleOne.setRole(RoleUtil.adminRole());
		userRoleOne.setUser(UserUtil.userAdmin());
		long id = 1;
		
		Mockito.when(userRoleRepository.updateUserRole(userRoleOne)).thenReturn(userRoleOne);
		
		assertThrows(ObjectIdNotFound.class, ()->{userRoleService.updateUserRole(id, userRoleToUpdate);});
	}
	
	@Test
	void givenUserRole_whenGet_thenValidateUserAndRole() {
		UserRoleEntity userRoleOne = new UserRoleEntity();
		userRoleOne.setRole(RoleUtil.adminRole());
		userRoleOne.setUser(UserUtil.userAdmin());
		long id = 1;
		
		Mockito.when(userRoleRepository.getUserRoleById(id)).thenReturn(userRoleOne);
		UserRoleDto userRoleToGet = userRoleService.getUserRoleById(id);
		
		assertNotNull(userRoleToGet);
		assertEquals(userRoleToGet.getRole().getName(), userRoleOne.getRole().getName());
		assertEquals(userRoleToGet.getUser().getUsername(), userRoleOne.getUser().getUsername());
	}
	
	@Test
	void givenWrongId_whenGet_thenThrowException() {
		long id = 1;
		
		Mockito.when(userRoleRepository.getUserRoleById(id)).thenReturn(null);
		
		assertThrows(ObjectIdNotFound.class, ()->{
			userRoleService.getUserRoleById(id);
		});
	}
	
	@Test
	void givenWrongUser_whenCreateUserRole_thenThrowException() {
		UserRoleCreateUpdateDto userRole = new UserRoleCreateUpdateDto();
		userRole.setUsername("Artur");
		Mockito.when(userRepository.getUserByUsername(userRole.getUsername())).thenReturn(null);
		
		assertThrows(ObjectFilteredNotFound.class, ()->{
			userRoleService.createUserRole(userRole);
		});
	}
	
	@Test
	void givenWrongRole_whenCreateUserRole_thenThrowException() {
		UserEntity user = UserUtil.userTest();
		UserRoleCreateUpdateDto userRole = new UserRoleCreateUpdateDto();
		userRole.setUsername(user.getUsername());
		userRole.setRole("ADMIN");
		
		
		Mockito.when(userRepository.getUserByUsername(userRole.getUsername())).thenReturn(user);
		Mockito.when(roleRepository.getRole(userRole.getRole())).thenReturn(null);
		
		
		assertThrows(CustomExceptionMessage.class, ()->{
			userRoleService.createUserRole(userRole);
		});
	}
	
	@Test
	void givenUserRole_whenCreateUserRole_thenValidateConnection() {
		RoleEntity role = RoleUtil.adminRole();
		List<RoleEntity> rolesList = new ArrayList<>();
		rolesList.add(role);
		UserEntity user = UserUtil.userTest();
		UserRoleCreateUpdateDto userRole = new UserRoleCreateUpdateDto();
		userRole.setUsername(user.getUsername());
		userRole.setRole("ADMIN");
		
		
		Mockito.when(userRepository.getUserByUsername(userRole.getUsername())).thenReturn(user);
		Mockito.when(roleRepository.getRole(userRole.getRole())).thenReturn(role);
		Mockito.when(roleRepository.getUserRole(user)).thenReturn(rolesList);
		
		assertThrows(CustomExceptionMessage.class, ()->{
			userRoleService.createUserRole(userRole);
		});
	}
	
	@Test
	void givenUserRole_whenCreateUserRole_thenGetDto() {
		RoleEntity role = RoleUtil.adminRole();
		List<RoleEntity> rolesList = new ArrayList<>();
		rolesList.add(RoleUtil.studentRole());
		UserEntity user = UserUtil.userTest();
		UserRoleCreateUpdateDto userRole = new UserRoleCreateUpdateDto();
		userRole.setUsername(user.getUsername());
		userRole.setRole("ADMIN");
		 UserRoleEntity userRoleToAdd = new UserRoleEntity();
		 userRoleToAdd.setRole(role);
		 userRoleToAdd.setUser(user);

		
		
		Mockito.when(userRepository.getUserByUsername(userRole.getUsername())).thenReturn(user);
		Mockito.when(roleRepository.getRole(userRole.getRole())).thenReturn(role);
		Mockito.when(roleRepository.getUserRole(user)).thenReturn(rolesList);
		Mockito.doNothing().when(userRoleRepository).saveUserRole(userRoleToAdd);
		
		UserRoleDto dto = userRoleService.createUserRole(userRole);
		
		assertEquals(user.getUsername(), dto.getUser().getUsername());
		assertEquals(user.getEmail(), dto.getUser().getEmail());
		assertEquals(role.getName(), dto.getRole().getName());
		assertNotNull(dto);
	}
	
	
	@Test
	void givenWrongUsername_whenUpdateUserRole_ThrowException() {
		long id = 1;
		UserRoleEntity userRole = new UserRoleEntity();
		UserRoleCreateUpdateDto userRoleDto = new UserRoleCreateUpdateDto();
		userRoleDto.setUsername("test");
		
		Mockito.when(userRoleRepository.getUserRoleById(id)).thenReturn(userRole);
		Mockito.when(userRepository.getUserByUsername(userRoleDto.getUsername())).thenReturn(null);
		
		assertThrows(ObjectIdNotFound.class, ()->{
			userRoleService.updateUserRole(id, userRoleDto);
		});
	}
	
	@Test
	void givenWrongRole_whenUpdateUserRole_ThrowException() {
		long id = 1;
		UserEntity user = UserUtil.userAdmin();
		UserRoleEntity userRole = new UserRoleEntity();
		UserRoleCreateUpdateDto userRoleDto = new UserRoleCreateUpdateDto();
		userRoleDto.setUsername("test");
		userRoleDto.setRole("ADMIN");
		
		Mockito.when(userRoleRepository.getUserRoleById(id)).thenReturn(userRole);
		Mockito.when(userRepository.getUserByUsername(userRoleDto.getUsername())).thenReturn(user);
		Mockito.when(roleRepository.getRole(userRoleDto.getRole())).thenReturn(null);
		
		assertThrows(ObjectIdNotFound.class, ()->{
			userRoleService.updateUserRole(id, userRoleDto);
		});
	}
	
	@Test
	void givenUserRole_whenUpdateUserRole_ThenValidate() {
		long id = 1;
		UserEntity user = UserUtil.userAdmin();
		RoleEntity role = RoleUtil.adminRole();
		List<RoleEntity> roleList = new ArrayList<>();
		roleList.add(RoleUtil.secretaryRole());
		List<RoleEntity> roleListMatch = new ArrayList<>();
		roleListMatch.add(role);
		UserRoleEntity userRole = new UserRoleEntity();
		UserRoleCreateUpdateDto userRoleDto = new UserRoleCreateUpdateDto();
		userRoleDto.setUsername("test");
		userRoleDto.setRole("ADMIN");
		
		Mockito.when(userRoleRepository.getUserRoleById(id)).thenReturn(userRole);
		Mockito.when(userRepository.getUserByUsername(userRoleDto.getUsername())).thenReturn(user);
		Mockito.when(roleRepository.getRole(userRoleDto.getRole())).thenReturn(role);
		Mockito.when(roleRepository.getUserRole(user)).thenReturn(roleListMatch);
		
		assertThrows(CustomExceptionMessage.class, ()->{userRoleService.updateUserRole(id, userRoleDto);});
	
		Mockito.when(roleRepository.getUserRole(user)).thenReturn(roleList);
		
		userRole.setRole(role);
		userRole.setUser(user);
		Mockito.when(userRoleRepository.updateUserRole(userRole)).thenReturn(userRole);
		UserRoleDto dtoReturned = userRoleService.updateUserRole(id, userRoleDto);
		
		assertEquals(role.getName(), dtoReturned.getRole().getName());
		assertEquals(user.getUsername(), dtoReturned.getUser().getUsername());
		assertNotNull(dtoReturned);
	}
	
	@Test
	void givenId_whenDelete_thenValidate() {
		long id = 1;
		List<RoleEntity> listOne = new ArrayList<>();
		List<RoleEntity> listTwo = new ArrayList<>();
		UserEntity user = UserUtil.userAdmin();
		UserRoleEntity userRole = new UserRoleEntity();
		userRole.setUser(user);
		listOne.add(RoleUtil.adminRole());
		listTwo.add(RoleUtil.adminRole());
		listTwo.add(RoleUtil.secretaryRole());
		
		Mockito.when(userRoleRepository.getUserRoleById(id)).thenReturn(null);
		
		assertThrows(ObjectIdNotFound.class, ()->{userRoleService.deleteUserRole(id);});
		
		Mockito.when(userRoleRepository.getUserRoleById(id)).thenReturn(userRole);
		Mockito.when(roleRepository.getUserRole(userRole.getUser())).thenReturn(listOne);
		
		assertThrows(CustomExceptionMessage.class, ()->{userRoleService.deleteUserRole(id);});
		
		Mockito.when(userRoleRepository.getUserRoleById(id)).thenReturn(userRole);
		Mockito.when(roleRepository.getUserRole(userRole.getUser())).thenReturn(listTwo);
		
		assertDoesNotThrow(()->userRoleService.deleteUserRole(id));
	}
	
}
