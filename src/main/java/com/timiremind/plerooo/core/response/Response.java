package com.timiremind.plerooo.core.response;

public record Response<T>(boolean status, T message) {}
