package com.switchfully.eurder.item.domain.exceptions;

public class InvalidDescriptionFormatException extends RuntimeException{
    public InvalidDescriptionFormatException() {
        super("Description cannot be empty.");
    }
}
