package com.example.subscriptionservice.messaging.producer;

import com.example.subscriptionservice.messaging.event.SubscriptionDowngradedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionEventProducer {

    private static final String TOPIC = "subscription-cache-invalidation";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishDowngraded(String login) {
        SubscriptionDowngradedEvent event =
                new SubscriptionDowngradedEvent(login);

        kafkaTemplate.send(TOPIC, login, event)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error(
                                "Не удалось отправить событие об изменении подписки для {}",
                                login,
                                exception
                        );
                    } else {
                        log.info(
                                "Событие об изменении подписки для {} отправлено в Kafka",
                                login
                        );
                    }
                });
    }
}