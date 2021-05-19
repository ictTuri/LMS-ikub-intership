package com.project.lms.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

import lombok.Data;

@Data
public class ErrorFormat {

	private HttpStatus status;
	private LocalDateTime timestamp;
	private String debugMessage;
	private String message;
	private String desc;
	private String suggestion;
	
	public ErrorFormat(HttpStatus badRequest, String error, HttpMessageNotReadableException ex) {
		this();
		this.status = badRequest;
		this.message = error;
		this.debugMessage = ex.getLocalizedMessage();
	}

	public ErrorFormat() {}

	public ErrorFormat(HttpStatus unauthorized) {
		this();
		this.status = unauthorized;
	}
}
