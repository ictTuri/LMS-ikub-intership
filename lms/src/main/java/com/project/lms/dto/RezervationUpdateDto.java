package com.project.lms.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RezervationUpdateDto {


	@NotBlank(message = "Username is mandatory!")
	private String username;

	@NotBlank(message = "Book Title is mandatory!")
	private String bookTitle;
}
