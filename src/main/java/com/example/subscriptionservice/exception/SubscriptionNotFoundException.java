package com.example.subscriptionservice.exception;

public class SubscriptionNotFoundException extends RuntimeException {

    public SubscriptionNotFoundException(String login) {
        super("Подписка для пользователя '" + login + "' не найдена");
    }
}