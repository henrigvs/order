package com.switchfully.eurder.order.domain.exceptions;

public class NegativeAmountOrdered extends RuntimeException{
    public NegativeAmountOrdered() {
        super("The amount ordered can't be negative");
    }
}
