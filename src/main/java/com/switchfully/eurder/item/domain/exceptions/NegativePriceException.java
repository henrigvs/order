package com.switchfully.eurder.item.domain.exceptions;

public class NegativePriceException extends RuntimeException{
    public NegativePriceException() {
        super("Price cannot be negative.");
    }
}
