package com.web.saga.services;

import com.web.saga.entities.InventoryEntity;
import com.web.saga.enums.InventoryStatus;
import com.web.saga.events.InventoryFailedEvent;
import com.web.saga.events.InventoryReservedEvent;
import com.web.saga.events.PaymentSuccessEvent;
import com.web.saga.repos.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${app.kafka.topics.inventory}")
    private String inventoryTopic;

    @KafkaListener(topics = "#{@paymentTopic.name}", groupId = "inventory-service")
    public void onPaymentEvent(PaymentSuccessEvent paymentSuccessEvent) {

        InventoryEntity inv = new InventoryEntity();
        inv.setOrderId(paymentSuccessEvent.getOrderId());
        inv.setProductId(paymentSuccessEvent.getProductId());
        inv.setQty(paymentSuccessEvent.getQty());

        if (paymentSuccessEvent.getQty() <= 50) {
            inv.setStatus(InventoryStatus.RESERVED);
            inventoryRepository.save(inv);
            kafkaTemplate.send(inventoryTopic, new InventoryReservedEvent(paymentSuccessEvent.getOrderId(), inv.getProductId(), inv.getQty()));
        } else {
            inv.setStatus(InventoryStatus.FAILED);
            inventoryRepository.save(inv);
            kafkaTemplate.send(inventoryTopic, new InventoryFailedEvent(paymentSuccessEvent.getOrderId(), inv.getProductId(), inv.getQty(), "OUT_OF_STOCK"));
        }
    }
}

