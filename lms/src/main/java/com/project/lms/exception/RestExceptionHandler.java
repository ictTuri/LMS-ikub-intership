package com.project.lms.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		return buildResponseEntity(new ErrorFormat(HttpStatus.BAD_REQUEST, error, ex));
	}

	@ExceptionHandler(InvalidPasswordException.class)
	protected ResponseEntity<Object> invalidPasswordException(InvalidPasswordException ex) {
		ErrorFormat errorFormat = new ErrorFormat(HttpStatus.UNAUTHORIZED);
		errorFormat.setMessage(ex.getLocalizedMessage());

		return buildResponseEntity(errorFormat);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	protected ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
		ErrorFormat errorFormat = new ErrorFormat(HttpStatus.NOT_FOUND);
		errorFormat.setMessage(ex.getLocalizedMessage());

		return buildResponseEntity(errorFormat);
	}

	private ResponseEntity<Object> buildResponseEntity(ErrorFormat errorFormat) {
		return new ResponseEntity<>(errorFormat, errorFormat.getStatus());
	}
}
