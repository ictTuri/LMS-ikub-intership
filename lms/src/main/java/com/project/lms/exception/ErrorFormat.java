package com.project.lms.exception;


import lombok.Data;

@Data
public class ErrorFormat {

	private String message;
	private String desc;
	private String suggestion;
}
