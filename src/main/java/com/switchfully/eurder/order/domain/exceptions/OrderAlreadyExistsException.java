package com.switchfully.eurder.order.domain.exceptions;

public class OrderAlreadyExistsException extends RuntimeException {
    public OrderAlreadyExistsException() {
        super("This order already exists");
    }
}
