package com.project.lms.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BookCreateUpdateDto {
	@NotBlank(message = "Title is Required!")
	private String title;
	
}
