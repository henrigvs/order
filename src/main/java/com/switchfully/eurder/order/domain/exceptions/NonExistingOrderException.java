package com.switchfully.eurder.order.domain.exceptions;

public class NonExistingOrderException extends RuntimeException {
    public NonExistingOrderException() {
        super("There is no order for this orderId");
    }
}
