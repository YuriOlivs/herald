package com.yuriolivs.notification.shared.exceptions.http;

public class HttpInternalServerErrorException extends HttpException {

    public HttpInternalServerErrorException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
