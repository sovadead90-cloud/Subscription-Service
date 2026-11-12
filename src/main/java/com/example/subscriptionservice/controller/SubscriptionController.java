package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.dto.SubscriptionResponse;
import com.example.subscriptionservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/{login}")
    public SubscriptionResponse getByLogin(@PathVariable String login) {
        return subscriptionService.getByLogin(login);
    }
}