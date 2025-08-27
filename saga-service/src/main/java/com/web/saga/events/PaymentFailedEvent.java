package com.web.saga.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentFailedEvent {
    private Long orderId;
    private String reason;
}
