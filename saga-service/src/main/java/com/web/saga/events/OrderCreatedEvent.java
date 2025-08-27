package com.web.saga.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderCreatedEvent {
    private Long orderId;
    private Long productId;
    private Integer qty;
    private Long amount;
}