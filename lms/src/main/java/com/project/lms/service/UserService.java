package com.project.lms.service;

import com.project.lms.dto.CustomResponseDto;
import com.project.lms.dto.UserCreateUpdateDto;

public interface UserService {

	CustomResponseDto registerStudent(UserCreateUpdateDto user);

}
