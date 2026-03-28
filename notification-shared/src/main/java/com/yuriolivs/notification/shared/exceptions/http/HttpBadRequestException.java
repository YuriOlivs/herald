package com.yuriolivs.notification.shared.exceptions.http;

public class HttpBadRequestException extends HttpException {

    public HttpBadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}

