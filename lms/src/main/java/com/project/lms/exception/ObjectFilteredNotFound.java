package com.project.lms.exception;

public class ObjectFilteredNotFound extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public ObjectFilteredNotFound(String message) {
		super(message);
	}

}
