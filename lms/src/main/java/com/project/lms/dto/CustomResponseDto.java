package com.project.lms.dto;

import lombok.Data;

@Data
public class CustomResponseDto {
	
	private String message;

	public CustomResponseDto(String message) {
		super();
		this.message = message;
	}
	
	
}
