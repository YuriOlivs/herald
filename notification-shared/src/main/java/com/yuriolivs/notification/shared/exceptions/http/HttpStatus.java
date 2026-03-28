package com.yuriolivs.notification.shared.exceptions.http;

public enum HttpStatus {
    BAD_REQUEST("400"),
    FORBIDDEN("403"),
    INTERNAL_SERVER_ERROR("500"),
    NOT_FOUND("404"),
    UNAUTHORIZED("401");

    private final String code;

    HttpStatus(String code) {
        this.code = code;
    }

    public Integer getCode() {
        return Integer.parseInt(this.code);
    }
}
