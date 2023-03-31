package com.switchfully.eurder.item.domain.exceptions;

public class InvalidNameFormatException extends RuntimeException{

    public InvalidNameFormatException() {
        super("Name cannot be empty.");
    }
}
