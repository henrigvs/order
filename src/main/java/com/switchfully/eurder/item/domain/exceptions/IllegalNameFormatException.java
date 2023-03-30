package com.switchfully.eurder.item.domain.exceptions;

public class IllegalNameFormatException extends RuntimeException{

    public IllegalNameFormatException() {
        super("Name cannot be empty.");
    }
}
