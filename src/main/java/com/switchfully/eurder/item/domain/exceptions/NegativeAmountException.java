package com.switchfully.eurder.item.domain.exceptions;

public class NegativeAmountException extends RuntimeException{
    public NegativeAmountException() {
        super("Amount cannot be negative.");
    }
}
