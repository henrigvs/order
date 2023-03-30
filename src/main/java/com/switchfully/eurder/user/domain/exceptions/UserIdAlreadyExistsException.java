package com.switchfully.eurder.user.domain.exceptions;

public class UserIdAlreadyExistsException extends RuntimeException {
    public UserIdAlreadyExistsException() {
        super("This user ID already exists");
    }
}
