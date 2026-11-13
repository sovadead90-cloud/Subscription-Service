package com.example.subscriptionservice.service;

import com.example.subscriptionservice.domain.Subscription;
import com.example.subscriptionservice.domain.SubscriptionType;
import com.example.subscriptionservice.dto.SubscriptionResponse;
import com.example.subscriptionservice.exception.SubscriptionNotFoundException;
import com.example.subscriptionservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionResponse getByLogin(String login) {
        Subscription subscription = subscriptionRepository.findById(login)
                .orElseThrow(() -> new SubscriptionNotFoundException(login));

        boolean active = subscription.getType() == SubscriptionType.PAID
                && subscription.getExpiresAt().isAfter(Instant.now());

        return new SubscriptionResponse(
                subscription.getLogin(),
                subscription.getType(),
                subscription.getExpiresAt(),
                active
        );
    }

    @Transactional
    public List<String> downgradeExpiredSubscriptions(Instant now) {
        List<Subscription> expiredSubscriptions =
                subscriptionRepository.findAllByTypeAndExpiresAtLessThanEqual(
                        SubscriptionType.PAID,
                        now
                );

        expiredSubscriptions.forEach(Subscription::downgradeToFree);

        return expiredSubscriptions.stream()
                .map(Subscription::getLogin)
                .toList();
    }
}