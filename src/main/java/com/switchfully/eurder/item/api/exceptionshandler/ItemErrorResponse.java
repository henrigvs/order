package com.switchfully.eurder.item.api.exceptionshandler;

public record ItemErrorResponse(String message, int statusCode, String reason) {
}
