package com.web.saga.services;

import com.web.saga.entities.OrderEntity;
import com.web.saga.enums.OrderStatus;
import com.web.saga.events.PaymentFailedEvent;
import com.web.saga.repos.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventServiceImpl implements OrderEventService {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "#{@paymentTopic.name}", groupId = "order-service")
    public void onPaymentEvents(PaymentFailedEvent paymentFailedEvent) {
        OrderEntity o = orderRepository.findById(paymentFailedEvent.getOrderId()).orElseThrow();
        o.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(o);
    }

}
