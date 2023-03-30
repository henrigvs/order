package com.switchfully.eurder.user.domain.exceptions;

public class InvalidUserIdFormatException extends RuntimeException {
    public InvalidUserIdFormatException() {
        super("User ID can only contains letters, digits and _ ");
    }
}
