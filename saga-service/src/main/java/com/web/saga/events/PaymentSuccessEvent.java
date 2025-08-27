package com.web.saga.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentSuccessEvent {
    private Long orderId;
    private Long amount;
}
