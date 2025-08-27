package com.web.saga.entities;

import com.web.saga.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "INVENTORY")
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INVENTORY_SEQ")
    @SequenceGenerator(name = "INVENTORY_SEQ", sequenceName = "saga_mgmt.INVENTORY_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORDER_ID", nullable = false)
    private Long orderId;
    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;
    @Column(name = "QTY", nullable = false)
    private Integer qty;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    private InventoryStatus status;
}

