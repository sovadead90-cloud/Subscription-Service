package com.example.subscriptionservice.dto;

import com.example.subscriptionservice.domain.SubscriptionType;

import java.time.Instant;

public record SubscriptionResponse(
        String login,
        SubscriptionType type,
        Instant expiresAt,
        boolean active
) {
}