package com.example.spring_security.exception;

public class UserFoundException extends RuntimeException{
    public UserFoundException(String message) {
        super(message);
    }
}
