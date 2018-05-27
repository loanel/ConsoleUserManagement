package com.data.test.ConsoleUserManagement.exception;

public class WrongLoginCredentialsException extends Exception {
    public WrongLoginCredentialsException(String message) {
        super(message);
    }

    public WrongLoginCredentialsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
