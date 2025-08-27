package com.web.saga.entities;

import com.web.saga.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PAYMENTS")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENTS_SEQ")
    @SequenceGenerator(name = "PAYMENTS_SEQ", sequenceName = "saga_mgmt.PAYMENTS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORDER_ID", nullable = false)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    private PaymentStatus status;

    @Column(name = "AMOUNT", nullable = false)
    private Long amount;
}

