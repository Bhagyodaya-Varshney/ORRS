package com.example.user_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.user_service.dto.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorDetails> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidRoleException.class)
	public ResponseEntity<ErrorDetails> handleInvalidRoleException(InvalidRoleException ex) {
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AuthenticationFailedException.class)
	public ResponseEntity<ErrorDetails> handleAuthenticationFailedException(AuthenticationFailedException ex) {
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException ex){
    	
    	//This line goes through the list of validation errors caused by invalid input in your DTO,
    	//picks the first one, and extracts the custom message you defined (like "Train Type is mandatory"),
    	//so that instead of returning a long technical error, your API responds with a clear and user-friendly message.
    	String errorMessage = ex.getBindingResult()
    						.getFieldErrors()
    						.stream()
							.findFirst()
							.map(error -> error.getDefaultMessage())
							.orElse("Validation failed");

    	ErrorDetails errorDetails = new ErrorDetails(errorMessage);
    	return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGeneralException(Exception ex) {
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
