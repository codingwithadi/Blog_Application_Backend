package com.blogappapis.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blogappapis.utilities.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handle All Exception in Controller
	// ExceptionHandler is handle specific exception class or method
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {

		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}

	// Handle Create User Exception or Validation Exception
	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> response = new HashMap<>();

		// getBIndingResult()->Binded result, getAllError()->List of Error,
		// forEach()->go through all error.
		ex.getBindingResult().getAllErrors().forEach((error) -> {

			// feildError -> for current Error
			String feildName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();

			// adding response fieldName and message and show to client. what is error and
			// reason of error.
			response.put(feildName, message);
		});

		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
	}
}
