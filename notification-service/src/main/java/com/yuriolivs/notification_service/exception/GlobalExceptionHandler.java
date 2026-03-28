package com.yuriolivs.notification_service.exception;

import com.yuriolivs.notification.shared.exceptions.ErrorResponse;
import com.yuriolivs.notification.shared.exceptions.http.HttpException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ErrorResponse> handleHttpException(
            HttpException ex,
            HttpServletRequest request
    ) {
        ErrorResponse err = new ErrorResponse(
                ex.getStatus().getCode(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(err.getStatusCode())
                .body(err);
    }
}
