package com.switchfully.eurder.item.domain.exceptions;

public class ItemIdDoesNotExistException extends RuntimeException{
    public ItemIdDoesNotExistException(String itemId) {
        super("Item with ID: " + itemId + " does not exist.");
    }
}
