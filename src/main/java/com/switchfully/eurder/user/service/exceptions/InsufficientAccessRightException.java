package com.switchfully.eurder.user.service.exceptions;

public class InsufficientAccessRightException extends RuntimeException {
    public InsufficientAccessRightException() {
        super("Insufficient rights to process this request");
    }
}
