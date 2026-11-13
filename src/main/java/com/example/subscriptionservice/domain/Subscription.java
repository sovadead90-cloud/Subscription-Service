package com.example.subscriptionservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @Column(name = "login", nullable = false, updatable = false)
    private String login;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private SubscriptionType type;

    @Column(name = "expires_at")
    private Instant expiresAt;

    public Subscription(String login, SubscriptionType type, Instant expiresAt) {
        this.login = login;
        this.type = type;
        this.expiresAt = expiresAt;
    }

    public void downgradeToFree() {
        this.type = SubscriptionType.FREE;
        this.expiresAt = null;
    }
}