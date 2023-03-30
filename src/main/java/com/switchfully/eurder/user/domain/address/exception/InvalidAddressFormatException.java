package com.switchfully.eurder.user.domain.address.exception;

public class InvalidAddressFormatException extends RuntimeException {
    public InvalidAddressFormatException(String message) {
        super("Invalid address format, " + message);
    }
}
