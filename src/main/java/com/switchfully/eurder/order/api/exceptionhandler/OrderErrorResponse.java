package com.switchfully.eurder.order.api.exceptionhandler;

public record OrderErrorResponse (String message, int statusCode, String reason){
}
