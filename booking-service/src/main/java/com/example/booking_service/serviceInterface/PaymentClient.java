package com.example.booking_service.serviceInterface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {

    @PostMapping("/payment/status/{fare}")
    String generateOrder(@PathVariable int fare);


    @GetMapping("/payment/verifyPayment/{paymentId}")
    boolean verifyPayment(@PathVariable String paymentId);

}
