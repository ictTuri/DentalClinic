package com.clinic.dental.config;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

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

import com.clinic.dental.exceptions.CustomMessageException;
import com.clinic.dental.exceptions.DataFilteredNotFoundException;
import com.clinic.dental.exceptions.DataIdNotFoundException;
import com.clinic.dental.exceptions.ErrorFormat;
import com.clinic.dental.exceptions.InvalidCredentialsException;
import com.clinic.dental.exceptions.UserNotFoundException;

import lombok.var;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

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

	@ExceptionHandler(value = { CustomMessageException.class, BadCredentialsException.class })
	protected ResponseEntity<Object> handleCustomExceptions(RuntimeException ex, WebRequest request) {
		var errorBody = new ErrorFormat();
		errorBody.setMessage(ex.getMessage());
		errorBody.setDesc(request.getDescription(false));
		errorBody.setSuggestion("Contact Admin");

		return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = { InvalidCredentialsException.class })
	protected ResponseEntity<Object> handleBadCredentials(RuntimeException ex, WebRequest request) {
		var errorBody = new ErrorFormat();
		errorBody.setMessage(ex.getMessage());
		errorBody.setDesc(request.getDescription(false));
		errorBody.setSuggestion("Check Credentials!");

		return new ResponseEntity<>(errorBody, HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(value = { DataFilteredNotFoundException.class, DataIdNotFoundException.class, UserNotFoundException.class })
	protected ResponseEntity<Object> handleNotFoundExceptions(RuntimeException ex, WebRequest request) {
		var errorBody = new ErrorFormat();
		errorBody.setMessage(ex.getMessage());
		errorBody.setDesc(request.getDescription(false));
		errorBody.setSuggestion("Contact Admin");

		return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(value = { ConstraintViolationException.class})
	protected ResponseEntity<Object> handleValidationConstraint(RuntimeException ex, WebRequest request){
		var errorBody = new ErrorFormat();
		errorBody.setMessage(ex.getMessage());
		errorBody.setDesc(request.getDescription(false));
		errorBody.setSuggestion("Contact Admin");
		
		return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
	}

}
