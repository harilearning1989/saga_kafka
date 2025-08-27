package com.web.saga.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InventoryReservedEvent {
    private Long orderId;
    private Long productId;
    private Integer qty;
}
