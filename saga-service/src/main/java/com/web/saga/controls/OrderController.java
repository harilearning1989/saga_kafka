package com.web.saga.controls;

import com.web.saga.entities.OrderEntity;
import com.web.saga.enums.OrderStatus;
import com.web.saga.events.OrderCreatedEvent;
import com.web.saga.records.CreateOrderRequest;
import com.web.saga.repos.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@Validated
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${app.kafka.topics.order}")
    private String orderTopic;

    @PostMapping("/create")
    public OrderEntity placeOrder(@RequestBody @Validated CreateOrderRequest req) {
        OrderEntity order = new OrderEntity();
        order.setStatus(OrderStatus.PENDING);
        order.setProductId(req.productId());
        order.setQty(req.qty());
        order.setAmount(req.amount());
        order = orderRepo.save(order);

        kafkaTemplate.send(orderTopic, new OrderCreatedEvent(order.getId(), order.getProductId(), order.getQty(), order.getAmount()));
        return order;
    }
}

