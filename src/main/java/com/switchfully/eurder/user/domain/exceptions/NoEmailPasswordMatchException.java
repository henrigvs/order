package com.switchfully.eurder.user.domain.exceptions;

public class NoEmailPasswordMatchException extends RuntimeException {

    public NoEmailPasswordMatchException() {
        super("Incorrect password");
    }
}
