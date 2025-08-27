package com.web.saga.repos;

import com.web.saga.entities.PaymentEntity;
import com.web.saga.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    PaymentEntity findFirstByOrderIdAndStatus(Long orderId, PaymentStatus status);
}
