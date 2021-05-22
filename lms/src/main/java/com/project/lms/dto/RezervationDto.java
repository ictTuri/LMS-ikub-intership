package com.project.lms.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RezervationDto {

	private Long id;
	private String studentUsername;
	private String bookTitle;
	private LocalDateTime rezervationDate;
	private LocalDateTime returnDate;
}
