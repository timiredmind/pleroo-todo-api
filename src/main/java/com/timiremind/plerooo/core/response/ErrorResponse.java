package com.timiremind.plerooo.core.response;

public record ErrorResponse(
        String timestamp, int status, String error, String message, String path) {}
