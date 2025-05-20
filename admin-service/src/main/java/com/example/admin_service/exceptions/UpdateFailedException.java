package com.example.admin_service.exceptions;

public class UpdateFailedException extends RuntimeException {
	public UpdateFailedException(String message) {
		super(message);
	}
}