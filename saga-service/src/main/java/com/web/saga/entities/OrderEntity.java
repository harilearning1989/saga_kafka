package com.web.saga.entities;

import com.web.saga.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ORDERS")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq", sequenceName = "saga_mgmt.orders_seq", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    private OrderStatus status;

    @Column(name = "AMOUNT", nullable = false)
    private Long amount;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "QTY", nullable = false)
    private Integer qty;
}

