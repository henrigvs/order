package com.switchfully.eurder.user.domain.exceptions;

public class NonExistingUserIdException extends RuntimeException {

    public NonExistingUserIdException() {
        super("User ID not found");
    }
}
