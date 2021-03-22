package com.ctf.jwtjku.model.exception;

public class CreateUserException extends Exception {
    public CreateUserException() {
    }

    public CreateUserException(String message) {
        super(message);
    }

    public CreateUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateUserException(Throwable cause) {
        super(cause);
    }
}
