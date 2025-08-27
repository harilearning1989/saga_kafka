package com.web.saga.events;

import com.web.saga.entities.OrderEntity;
import com.web.saga.enums.OrderStatus;
import com.web.saga.repos.OrderRepository;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "#{@inventoryTopic.name}", groupId = "order-service")
public class InventoryEventHandler {

    private final OrderRepository orderRepository;

    public InventoryEventHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @KafkaHandler
    public void handleReserved(InventoryReservedEvent e) {
        OrderEntity o = orderRepository.findById(e.getOrderId()).orElseThrow();
        o.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(o);
        System.out.println("✅ Order " + e.getOrderId() + " confirmed after inventory reserved");
    }

    @KafkaHandler
    public void handleFailed(InventoryFailedEvent e) {
        OrderEntity o = orderRepository.findById(e.getOrderId()).orElseThrow();
        o.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(o);
        System.out.println("❌ Order " + e.getOrderId() + " cancelled after inventory failed");
    }

    // optional catch-all
    @KafkaHandler(isDefault = true)
    public void handleDefault(Object event) {
        System.out.println("⚠️ Unknown inventory event: " + event);
    }
}


