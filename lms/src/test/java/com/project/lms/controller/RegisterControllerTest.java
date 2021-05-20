package com.project.lms.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.lms.dto.CustomResponseDto;
import com.project.lms.dto.UserRegisterDto;
import com.project.lms.exception.CustomExceptionMessage;
import com.project.lms.service.UserService;

@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

	@InjectMocks
	RegisterController registerController;

	@Mock
	UserService userService;

	@BeforeEach
	void setup() {
		registerController = new RegisterController(userService);
	}

	@Test
	void givenNewRegistrant_whenSave_thenVerifySave() {
		UserRegisterDto studentDto = new UserRegisterDto();
		studentDto.setFirstName("student");
		studentDto.setLastName("student");
		studentDto.setEmail("student@gmail.com");
		studentDto.setPassword("password");
		studentDto.setUsername("studentBest");

		CustomResponseDto response = new CustomResponseDto("Registration complete");

		Mockito.when(userService.registerStudent(studentDto)).thenReturn(response);

		ResponseEntity<CustomResponseDto> customResponse = registerController.registerStudent(studentDto);

		assertNotNull(customResponse);
		assertEquals(HttpStatus.CREATED, customResponse.getStatusCode());
		assertEquals("Registration complete", customResponse.getBody().getMessage());
	}

	@Test
	void givenNewRegistrant_whenEmailExist_thenThrowException() {
		UserRegisterDto studentDto = new UserRegisterDto();
		studentDto.setFirstName("student");
		studentDto.setLastName("student");
		studentDto.setEmail("student@gmail.com");
		studentDto.setPassword("password");
		studentDto.setUsername("studentBest");

		Mockito.when(userService.registerStudent(studentDto)).thenThrow(new CustomExceptionMessage("Email Exist"));

		assertThrows(CustomExceptionMessage.class, () -> {registerController.registerStudent(studentDto);});
	}

}
