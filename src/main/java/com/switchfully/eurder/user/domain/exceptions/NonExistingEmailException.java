package com.switchfully.eurder.user.domain.exceptions;

public class NonExistingEmailException extends RuntimeException {
    public NonExistingEmailException() {
        super("Email not found");
    }
}
