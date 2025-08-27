package com.web.saga.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InventoryFailedEvent {
    private Long orderId;
    private Long productId;
    private Integer qty;
    private String reason;
}
