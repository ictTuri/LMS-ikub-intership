package com.project.lms.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.project.lms.exception.ErrorFormat;
import com.project.lms.exception.InvalidCredentialsException;
import com.project.lms.exception.ObjectFilteredNotFound;
import com.project.lms.exception.ObjectIdNotFound;
import com.project.lms.exception.UserNotFoundException;
import com.project.lms.exception.CustomExceptionMessage;

@Component
@RestControllerAdvice
public class MyExceptionHandler{

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
			errors.put("error", "Validation Error");
		});
		return errors;
	}

	@ExceptionHandler(value = { CustomExceptionMessage.class, 
			BadCredentialsException.class})
	protected ResponseEntity<Object> handleCustomExceptions(RuntimeException ex, WebRequest request) {
		var errorBody = new ErrorFormat();
		errorBody.setMessage(ex.getMessage());
		errorBody.setDesc(request.getDescription(false));
		errorBody.setSuggestion("Contact Admin");

		return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(value = {InvalidCredentialsException.class})
	protected ResponseEntity<Object> handleBadCredentials(RuntimeException ex, WebRequest request) {
		var errorBody = new ErrorFormat();
		errorBody.setMessage(ex.getMessage());
		errorBody.setDesc(request.getDescription(false));
		errorBody.setSuggestion("Check Credentials!");

		return new ResponseEntity<>(errorBody, HttpStatus.UNAUTHORIZED);

	}
	
	@ExceptionHandler(value = {ObjectFilteredNotFound.class, 
			ObjectIdNotFound.class, 
			UserNotFoundException.class})
	protected ResponseEntity<Object> handleNotFoundExceptions(RuntimeException ex, WebRequest request) {
		var errorBody = new ErrorFormat();
		errorBody.setMessage(ex.getMessage());
		errorBody.setDesc(request.getDescription(false));
		errorBody.setSuggestion("Contact Admin");

		return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);

	}

}
