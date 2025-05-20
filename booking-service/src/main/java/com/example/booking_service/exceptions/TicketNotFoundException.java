package com.example.booking_service.exceptions;

public class TicketNotFoundException extends RuntimeException{

	public TicketNotFoundException(String message) {
		super(message);
	}
	
}
