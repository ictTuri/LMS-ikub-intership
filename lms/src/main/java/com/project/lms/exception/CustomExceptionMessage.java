package com.project.lms.exception;

public class CustomExceptionMessage extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CustomExceptionMessage(String message) {
		super(message);
	}

}
