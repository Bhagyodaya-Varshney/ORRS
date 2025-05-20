package com.example.payment_service.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.payment_service.model.PaymentModel;
import com.example.payment_service.serviceInterface.ServiceInterface;

@RestController
@RequestMapping("/payment")
public class Controller {

    @Autowired
    ServiceInterface service;

    // Generate Order (call Razorpay API)
    @PostMapping("/status/{fare}")
    public String generateOrder(@PathVariable int fare) {
        return service.ProcessPayment(fare);
    }

    // Payment status handler
    @PostMapping("/payment-status")
    public void paymentStatus(@RequestBody Map<String, String> paymentResponse) {
        String paymentId = paymentResponse.get("payment_id");
        String status = paymentResponse.get("status");
        String orderId = paymentResponse.get("order_id");

        if ("SUCCESS".equals(status)) {
            service.setPaymentIdAndStatus(orderId,paymentId,status);
        }
        else {
        	service.setPaymentIdAndStatus(orderId,"",status);
        }
    }
    
    @GetMapping("/getOrderAmount")
    public PaymentModel getOrderDetails() {
    	return service.getOrderAmount();
    }

    
    // Verify Payment status
    @GetMapping("/verifyPayment/{paymentId}")
    public boolean verifyPayment(@PathVariable String paymentId) {
        return service.verifyPaymentStatus(paymentId);
    }
}
