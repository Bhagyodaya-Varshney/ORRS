package com.example.railway_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.railway_service.dto.ErrorDetails;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TrainAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleTrainAlreadyExistsException(TrainAlreadyExistsException ex) {
    	ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TrainNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleTrainNotFoundException(TrainNotFoundException ex) {
    	ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCoachException.class)
    public ResponseEntity<ErrorDetails> handleInvalidCoachException(InvalidCoachException ex) {
    	ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGeneralException(Exception ex) {
    	ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
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
}