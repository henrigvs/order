package com.switchfully.eurder.user.domain.exceptions;

public class InvalidFirstNameFormatException extends RuntimeException {
    public InvalidFirstNameFormatException() {
        super("Invalid format for the firstName, must only contains letters");
    }
}
