package com.web.saga.services;

import com.web.saga.entities.PaymentEntity;
import com.web.saga.enums.PaymentStatus;
import com.web.saga.events.InventoryFailedEvent;
import com.web.saga.events.OrderCreatedEvent;
import com.web.saga.events.PaymentFailedEvent;
import com.web.saga.events.PaymentSuccessEvent;
import com.web.saga.repos.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${app.kafka.topics.payment}")
    private String paymentTopic;

    @KafkaListener(topics = "#{@orderTopic.name}", groupId = "payment-service")
    public void onOrderCreated(OrderCreatedEvent event) {
        PaymentEntity p = new PaymentEntity();
        p.setOrderId(event.getOrderId());
        p.setAmount(event.getAmount());

        if (event.getAmount() >= 10 && event.getAmount() <= 1000) {
            p.setStatus(PaymentStatus.SUCCESS);
            paymentRepo.save(p);
            kafkaTemplate.send(paymentTopic, new PaymentSuccessEvent(event.getOrderId(), event.getAmount(), event.getQty(), event.getProductId()));
        } else {
            p.setStatus(PaymentStatus.FAILED);
            paymentRepo.save(p);
            kafkaTemplate.send(paymentTopic, new PaymentFailedEvent(event.getOrderId(), "CARD_DECLINED"));
        }
    }

    // Compensation: refund when inventory fails after a successful payment
    @KafkaListener(topics = "#{@inventoryTopic.name}", groupId = "payment-service")
    public void onInventoryFailed(InventoryFailedEvent inventoryFailedEvent) {
        PaymentEntity success = paymentRepo.findFirstByOrderIdAndStatus(inventoryFailedEvent.getOrderId(), PaymentStatus.SUCCESS);
        if (success != null) {
            success.setStatus(PaymentStatus.REFUNDED);
            paymentRepo.save(success);
            // Optionally emit a PaymentRefunded event for other services
            // kafkaTemplate.send(paymentTopic, new PaymentRefundedEvent(failed.getOrderId(), success.getAmount()));
        }
    }
}

