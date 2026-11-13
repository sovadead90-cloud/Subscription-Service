package com.example.subscriptionservice.scheduler;

import com.example.subscriptionservice.service.SubscriptionService;
import com.example.subscriptionservice.messaging.producer.SubscriptionEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionExpirationScheduler {

    private final SubscriptionService subscriptionService;
    private final SubscriptionEventProducer eventProducer;

    @Scheduled(
            fixedDelayString = "${subscription.expiration.check-interval-ms:60000}"
    )
    @SchedulerLock(
            name = "subscriptionExpirationTask",
            lockAtMostFor = "${shedlock.lock-at-most-for}"
    )
    public void downgradeExpiredSubscriptions() {
        List<String> downgradedLogins =
                subscriptionService.downgradeExpiredSubscriptions(Instant.now());

        if (!downgradedLogins.isEmpty()) {
            log.info("Переведены на бесплатный тариф: {}", downgradedLogins);
            downgradedLogins.forEach(eventProducer::publishDowngraded);
        }
    }
}