package com.example.admin_service.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	public ResourceNotFoundException(String msg){
		super(msg);
	}
}