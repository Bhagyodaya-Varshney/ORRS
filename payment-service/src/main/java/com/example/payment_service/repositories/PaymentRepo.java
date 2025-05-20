package com.example.payment_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.payment_service.model.PaymentModel;

@Repository
public interface PaymentRepo extends JpaRepository<PaymentModel, Long> {
    PaymentModel findByOrderId(String orderId);
    PaymentModel findByPaymentId(String orderId);
}
