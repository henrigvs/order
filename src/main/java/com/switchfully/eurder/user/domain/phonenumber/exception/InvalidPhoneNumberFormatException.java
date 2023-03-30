package com.switchfully.eurder.user.domain.phonenumber.exception;

public class InvalidPhoneNumberFormatException extends RuntimeException{

    public InvalidPhoneNumberFormatException(String message) {
        super("Invalid phone number format, example :" + message);
    }
}
