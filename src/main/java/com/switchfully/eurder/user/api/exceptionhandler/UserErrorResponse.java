package com.switchfully.eurder.user.api.exceptionhandler;

public record UserErrorResponse(String message, int statusCode, String reason) {
}
