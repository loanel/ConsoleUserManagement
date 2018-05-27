package com.data.test.ConsoleUserManagement.exception;

public class WrongUserInformationException extends Exception {
    public WrongUserInformationException(String message) {
        super(message);
    }

    public WrongUserInformationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
