package com.switchfully.eurder.item.domain.exceptions;

public class ItemAlreadyExistException extends RuntimeException {
    public ItemAlreadyExistException(String itemId) {
        super("Item with ID: " + itemId + " already exist.");
    }
}
