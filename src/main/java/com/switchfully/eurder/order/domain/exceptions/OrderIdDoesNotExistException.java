package com.switchfully.eurder.order.domain.exceptions;

public class OrderIdDoesNotExistException extends RuntimeException {
    public OrderIdDoesNotExistException() {
        super("This order id does not exist");
    }
}
