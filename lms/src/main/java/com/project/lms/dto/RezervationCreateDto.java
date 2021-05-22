package com.project.lms.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RezervationCreateDto {

	@NotBlank(message = "Book title is mandatory!")
	private String bookTitle;
}
