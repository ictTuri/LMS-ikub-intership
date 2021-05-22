package com.project.lms.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RezervationCreateUpdateDto {

	@NotBlank(message = "Book title is mandatory!")
	private String bookTitle;
	
	@NotBlank(message = "Student username is mandatory!")
	private String username;
}
