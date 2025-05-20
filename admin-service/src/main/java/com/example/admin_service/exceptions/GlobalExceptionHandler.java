package com.example.admin_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.admin_service.dto.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(Exception.class)
	 public ResponseEntity<ErrorDetails> globalExceptionHandler(Exception ex, WebRequest request) {
	        ErrorDetails errorDetails = new ErrorDetails(ex.getClass().getSimpleName(), request.getDescription(false));
	        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UpdateFailedException.class)
    public ResponseEntity<ErrorDetails> updateFailedException(UpdateFailedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
