package com.project.lms.service;

import java.util.List;

import javax.validation.Valid;

import com.project.lms.dto.CustomResponseDto;
import com.project.lms.dto.UserCreateUpdateDto;
import com.project.lms.dto.UserRegisterDto;
import com.project.lms.dto.UserDto;

public interface UserService {

	CustomResponseDto registerStudent(@Valid UserRegisterDto user);

	List<UserDto> getAllUsers();

	UserDto getUserById(Long id);

	UserDto createUser(@Valid UserCreateUpdateDto user);

	UserDto updateUser(Long id, @Valid UserCreateUpdateDto user);

	void softDeleteUser(long id);

	void hardDeleteUser(long id);

}
