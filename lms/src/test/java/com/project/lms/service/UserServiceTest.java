package com.project.lms.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.lms.converter.UserConverter;
import com.project.lms.dto.CustomResponseDto;
import com.project.lms.dto.UserCreateUpdateDto;
import com.project.lms.dto.UserDto;
import com.project.lms.dto.UserRegisterDto;
import com.project.lms.enums.Roles;
import com.project.lms.exception.CustomExceptionMessage;
import com.project.lms.exception.ObjectIdNotFound;
import com.project.lms.model.RezervationEntity;
import com.project.lms.model.RoleEntity;
import com.project.lms.model.UserEntity;
import com.project.lms.model.UserRoleEntity;
import com.project.lms.repository.RezervationRepository;
import com.project.lms.repository.RoleRepository;
import com.project.lms.repository.UserRepository;
import com.project.lms.repository.UserRoleRepository;
import com.project.lms.service.impl.UserServiceImpl;
import com.project.lms.utils.RoleUtil;
import com.project.lms.utils.UserUtil;

@SpringBootTest
class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	@Mock
	private RezervationRepository rezervationRepository;
	
	@Mock
	private UserRoleRepository userRoleRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Test
	void givenUserList_whenGetThem_validateSize() {
		List<UserEntity> users = new ArrayList<>();
		users.add(UserUtil.userTest());
		users.add(UserUtil.userAdmin());

		when(userRepository.getAllUsers()).thenReturn(users);
		int size = userService.getAllUsers().size();
		
		assertEquals(2, size);
		verify(userRepository).getAllUsers();
	}
	
	@Test
	void givenId_whenGet_thenValidateUser() {
		UserEntity user = UserUtil.userAdmin();
		
		Mockito.when(userRepository.getUserById(Long.valueOf(1))).thenReturn(user);
		UserDto returned = userService.getUserById(Long.valueOf(1));
		
		assertNotNull(returned);
		assertEquals(returned.getUsername(), user.getUsername());
	}
	
	@Test
	void givenStudent_whenExistUsername_thenThrowException() {
		UserRegisterDto student = new UserRegisterDto();
		
		Mockito.when(userRepository.existUsername(student.getUsername())).thenReturn(true);
		
		assertThrows(CustomExceptionMessage.class,()->{ userService.registerStudent(student);});
	}
	
	@Test
	void givenStudent_whenExistEmail_thenThrowException() {
		UserRegisterDto student = new UserRegisterDto();
		
		Mockito.when(userRepository.existUsername(student.getUsername())).thenReturn(false);
		Mockito.when(userRepository.existEmail(student.getEmail())).thenReturn(true);
		
		assertThrows(CustomExceptionMessage.class,()->{ userService.registerStudent(student);},
				"Email: "+student.getEmail()+" already exist");
	}
	
	@Test
	void givenWrongId_whenGetUser_thenThrowException() {
		long id = 1;
		
		Mockito.when(userRepository.getUserById(id)).thenReturn(null);
		
		assertThrows(ObjectIdNotFound.class,()->{ userService.getUserById(id);},
				"User with id: " + id + " can not be found");
	}
	
	@Test
	void givenStudent_whenCreate_thenGetResponseDto() {
		UserRegisterDto student = new UserRegisterDto();
		student.setEmail("helloworld@gmail.com");
		RoleEntity role = RoleUtil.studentRole();
		UserRoleEntity userRole = new UserRoleEntity();
		
		
		Mockito.when(userRepository.existUsername(student.getUsername())).thenReturn(false);
		Mockito.when(userRepository.existEmail(student.getEmail())).thenReturn(false);
		Mockito.when(roleRepository.getRole("STUDENT")).thenReturn(role);
		Mockito.when(passwordEncoder.encode(student.getPassword())).thenReturn("GjiberishHere");
		
		UserEntity userToSave = UserConverter.toEntity(student);
		userRole.setRole(role);
		userRole.setUser(userToSave);
		
		doNothing().when(userRepository).saveUser(userToSave);
		doNothing().when(userRoleRepository).saveUserRole(userRole);
		
		CustomResponseDto response = userService.registerStudent(student);
		
		assertEquals(response.getClass().getTypeName(), CustomResponseDto.class.getTypeName());
		assertEquals("You have been register, please wait for the activation", response.getMessage());
	}
	
	@Test
	void givenExistingUsername_whenCreateUser_thenThrowException() {
		UserCreateUpdateDto userDto = new UserCreateUpdateDto();
		userDto.setEmail("test@gmail.com");
		userDto.setUsername("test");
		userDto.setRole("ADMIN");
		
		Mockito.when(userRepository.checkUserByUsernameEmail(userDto.getUsername(), userDto.getEmail())).thenReturn(true);
		
		assertThrows(CustomExceptionMessage.class,()->{userService.createUser(userDto);});
		
	}
	
	@Test
	void givenWrongRole_whenCreateUser_thenThrowException() {
		UserCreateUpdateDto userDto = new UserCreateUpdateDto();
		userDto.setEmail("test@gmail.com");
		userDto.setUsername("test");
		userDto.setRole("ADMIN");
		
		Mockito.when(userRepository.checkUserByUsernameEmail(userDto.getUsername(), userDto.getEmail())).thenReturn(false);
		Mockito.when(roleRepository.getRole(Roles.valueOf(userDto.getRole()).toString())).thenReturn(null);
		
		assertThrows(CustomExceptionMessage.class,()->{userService.createUser(userDto);},
				"Given Role is not valid,try Admin or Student or Secretary");
		
	}
	
	
	@Test
	void givenUser_whenCreateUser_thenGetDto() {
		UserCreateUpdateDto userDto = new UserCreateUpdateDto();
		RoleEntity thisRole = RoleUtil.secretaryRole();
		List<RoleEntity> list = new ArrayList<>();
		list.add(thisRole);
		userDto.setEmail("test@gmail.com");
		userDto.setUsername("test");
		userDto.setRole("SECRETARY");

		
		Mockito.when(userRepository.checkUserByUsernameEmail(userDto.getUsername(), userDto.getEmail())).thenReturn(false);
		Mockito.when(roleRepository.getRole(Roles.valueOf(userDto.getRole()).toString())).thenReturn(thisRole);
		Mockito.when(passwordEncoder.encode(userDto.getPassword())).thenReturn("GjiberishPassword");
		
		UserEntity userToSave = UserConverter.toEntity(userDto);
		Mockito.doNothing().when(userRepository).saveUser(userToSave);
		UserRoleEntity userRole = new UserRoleEntity();
		doNothing().when(userRoleRepository).saveUserRole(userRole);
		
		Mockito.when(userRepository.getUserByUsername(userToSave.getUsername())).thenReturn(userToSave);
		Mockito.when(roleRepository.getUserRole(userToSave)).thenReturn(list);
		
		UserDto returned = userService.createUser(userDto);
		
		assertNotNull(returned);
		assertEquals(userToSave.getUsername(), returned.getUsername());
		assertEquals(userToSave.getEmail(), returned.getEmail());
	}
	
	@Test
	void givenUserAndUserUpdate_whenCheckExist_thenReturnBoolean() {
		UserEntity userToUpdate = UserUtil.userAdmin();
		UserCreateUpdateDto user = new UserCreateUpdateDto();
		user.setUsername(userToUpdate.getUsername());
		
		Mockito.when(userRepository.existUsername(user.getUsername())).thenReturn(false);
		
		boolean existUsername = userService.existUsername(user, userToUpdate);
		assertFalse(existUsername);
		assertEquals(false, existUsername);
		assertNotEquals(true, existUsername);
	}
	
	@Test
	void givenUserAndUserUpdate_whenCheckExistEmail_thenReturnBoolean() {
		UserEntity userToUpdate = UserUtil.userAdmin();
		UserCreateUpdateDto user = new UserCreateUpdateDto();
		user.setUsername(userToUpdate.getUsername());
		user.setEmail(userToUpdate.getEmail());
		
		Mockito.when(userRepository.existEmail(user.getEmail())).thenReturn(false);
		
		boolean existEmail = userService.existEmail(user, userToUpdate);
		assertFalse(existEmail);
		assertEquals(false, existEmail);
		assertNotEquals(true, existEmail);
	}
	
	@Test
	void givenUserAndUserUpdate_whenCheckExist_thenReturnTrue() {
		UserEntity userToUpdate = UserUtil.userAdmin();
		UserCreateUpdateDto user = new UserCreateUpdateDto();
		user.setUsername("test");
		
		Mockito.when(userRepository.existUsername(user.getUsername())).thenReturn(true);
		
		boolean existUsername = userService.existUsername(user, userToUpdate);
		assertTrue(existUsername);
		assertEquals(true, existUsername);
		assertNotEquals(false, existUsername);
	}
	
	@Test
	void givenUserAndUserUpdate_whenCheckExistEmail_thenReturnTrue() {
		UserEntity userToUpdate = UserUtil.userAdmin();
		UserCreateUpdateDto user = new UserCreateUpdateDto();
		user.setUsername(userToUpdate.getUsername());
		user.setEmail("test@gmail.com");
		
		Mockito.when(userRepository.existEmail(user.getEmail())).thenReturn(true);
		
		boolean existEmail = userService.existEmail(user, userToUpdate);
		assertTrue(existEmail);
		assertEquals(true, existEmail);
		assertNotEquals(false, existEmail);
	}
	
	@Test
	void givenRolesAndUser_whenCheckConnection_thenReturnBoolean() {
		List<RoleEntity> roles = new ArrayList<>();
		roles.add(RoleUtil.adminRole());
		roles.add(RoleUtil.studentRole());
		UserCreateUpdateDto user =  new UserCreateUpdateDto();
		user.setRole("ADMIN");
		
		boolean alreadyHasRole = userService.isUserRoleConnected(roles, user);
		
		assertTrue(alreadyHasRole);
		assertEquals(true, alreadyHasRole);
	}
	
	
	@Test
	void givenUser_whensoftDelete_thenThrowNothing() {
		long id = 1;
		UserEntity userToDelete = UserUtil.userTest();
		userToDelete.setId(id);
		userToDelete.setActivated(true);
		
		Mockito.when(userRepository.getActivatedUserById(id)).thenReturn(userToDelete);
		Mockito.doNothing().when(userRepository).updateUser(userToDelete);
		
		assertDoesNotThrow(()-> userService.softDeleteUser(id));
	}
	
	@Test
	void givenWrongId_whensoftDelete_thenThrowException() {
		long id = 1;
		
		Mockito.when(userRepository.getActivatedUserById(id)).thenReturn(null);
		
		assertThrows(ObjectIdNotFound.class, ()->{
			userService.softDeleteUser(id);
		});
	}
	
	@Test
	void givenUser_whenHardDelete_thenThrowNothing() {
		long id = 1;
		List<RezervationEntity> rezervationList = new ArrayList<>();
		UserEntity userToDelete = UserUtil.userTest();
		userToDelete.setId(id);
		userToDelete.setActivated(true);
		UserRoleEntity userRole = new UserRoleEntity();
		List<UserRoleEntity> list = new ArrayList<>();
		userRole.setRole(RoleUtil.adminRole());
		userRole.setUser(userToDelete);
		
		Mockito.when(rezervationRepository.myRezervations(userToDelete)).thenReturn(rezervationList);
		Mockito.when(userRoleRepository.getThisUserRelations(userToDelete)).thenReturn(list);
		Mockito.when(userRepository.getUserById(id)).thenReturn(userToDelete);
		
		Mockito.doNothing().when(userRepository).deleteUser(userToDelete);
		
		assertDoesNotThrow(()-> userService.hardDeleteUser(id));
	}
	
	@Test
	void givenWrongId_whenHardDelete_thenThrowException() {
		long id = 1;
		
		Mockito.when(userRepository.getActivatedUserById(id)).thenReturn(null);
		
		assertThrows(ObjectIdNotFound.class, ()->{
			userService.hardDeleteUser(id);
		});
	}
	
	@Test
	void givenDto_whenUpdate_thenGoThrow() {
		UserCreateUpdateDto userDto = new UserCreateUpdateDto();
		
		long id = 1;

		when(userRepository.getUserById(id)).thenReturn(null);
		
		assertThrows(ObjectIdNotFound.class, ()->userService.updateUser(id, userDto));
		
	}

	@Test
	void givenDtos_whenValidateUpdate_thenGoThrow() {
		UserCreateUpdateDto user = new UserCreateUpdateDto();
		UserEntity userToUpdate  = UserUtil.userAdmin();
		String roleName = "ADMIN";
		RoleEntity roleAdmin = RoleUtil.adminRole();
		RoleEntity role = RoleUtil.secretaryRole();
		List<RoleEntity> roleList = new ArrayList<>();
		roleList.add(role);
		
		Mockito.when(roleRepository.getRole(roleName)).thenReturn(null);
		
		assertThrows(CustomExceptionMessage.class, ()->{userService.updateValidationsExtracted(user, userToUpdate, roleName);});
		
		Mockito.when(roleRepository.getRole(roleName)).thenReturn(roleAdmin);
		
		UserDto dto = userService.updateValidationsExtracted(user, userToUpdate, roleName);
		assertNotNull(dto);	
	}
	
}
