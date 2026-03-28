package com.yuriolivs.notification.shared.exceptions.http;

public class HttpForbiddenException extends HttpException {

    public HttpForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}

