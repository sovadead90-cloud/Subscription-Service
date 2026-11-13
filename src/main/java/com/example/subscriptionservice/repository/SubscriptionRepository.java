package com.example.subscriptionservice.repository;

import com.example.subscriptionservice.domain.Subscription;
import com.example.subscriptionservice.domain.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface SubscriptionRepository
        extends JpaRepository<Subscription, String> {

    List<Subscription> findAllByTypeAndExpiresAtLessThanEqual(
            SubscriptionType type,
            Instant expirationTime
    );
}