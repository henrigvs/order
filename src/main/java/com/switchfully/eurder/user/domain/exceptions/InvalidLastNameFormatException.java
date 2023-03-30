package com.switchfully.eurder.user.domain.exceptions;

public class InvalidLastNameFormatException extends RuntimeException {
    public InvalidLastNameFormatException() {
        super("Invalid format for the lastName, must only contains letters");
    }
}
