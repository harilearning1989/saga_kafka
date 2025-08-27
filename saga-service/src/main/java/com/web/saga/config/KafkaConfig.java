package com.web.saga.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value("${app.kafka.topics.order}")
    private String orderTopic;
    @Value("${app.kafka.topics.payment}")
    private String paymentTopic;
    @Value("${app.kafka.topics.inventory}")
    private String inventoryTopic;

    @Bean
    public NewTopic orderTopic() {
        return new NewTopic(orderTopic, 3, (short) 1);
    }

    @Bean
    public NewTopic paymentTopic() {
        return new NewTopic(paymentTopic, 3, (short) 1);
    }

    @Bean
    public NewTopic inventoryTopic() {
        return new NewTopic(inventoryTopic, 3, (short) 1);
    }
}

