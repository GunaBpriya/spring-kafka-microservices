package com.example.notificationservice.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserSignupConsumer {

    private static final Logger logger = LoggerFactory.getLogger(UserSignupConsumer.class);

    @KafkaListener(topics = "user-signups", groupId = "notification-group")
    public void listen(String email) {
        logger.info("Received user signup email: {}", email);
    }
}