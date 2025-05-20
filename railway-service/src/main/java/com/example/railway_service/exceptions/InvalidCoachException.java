package com.example.railway_service.exceptions;
public class InvalidCoachException extends RuntimeException {
    public InvalidCoachException(String message) {
        super(message);
    }
}