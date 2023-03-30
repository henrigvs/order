package com.switchfully.eurder.item.domain.exceptions;

public class IllegalDescriptionFormatException extends RuntimeException{
    public IllegalDescriptionFormatException() {
        super("Description cannot be empty.");
    }
}
