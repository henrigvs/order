package com.switchfully.eurder.user.domain.exceptions;

public class InvalidPasswordFormatException extends RuntimeException {
    public InvalidPasswordFormatException() {
        super("Password can't be empty or null");

    }
}
