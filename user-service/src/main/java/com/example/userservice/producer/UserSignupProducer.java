package com.example.userservice.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserSignupProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public UserSignupProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSignupEmail(String email) {
        kafkaTemplate.send("user-signups", email);
    }
}