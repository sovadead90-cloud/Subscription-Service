package com.example.subscriptionservice.config;

import com.example.subscriptionservice.exception.SubscriptionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SubscriptionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleSubscriptionNotFound(
            SubscriptionNotFoundException exception
    ) {
        return exception.getMessage();
    }
}