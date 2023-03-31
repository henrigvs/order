package com.switchfully.eurder.order.domain.exceptions;

public class IllegalDateOfOrder extends RuntimeException{
    public IllegalDateOfOrder() {
        super("Shipping date cannot be before 7 next days.");
    }
}
