package com.project.lms.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RoleCreateUpdateDto {

	@NotBlank(message = "Role name is mandatory!")
	private String name;
}
