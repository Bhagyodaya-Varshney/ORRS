package com.example.payment_service.serviceInterface;

import org.json.JSONObject;

import com.example.payment_service.model.PaymentModel;

public interface ServiceInterface {
	String ProcessPayment(int fare);
	PaymentModel getOrderAmount();
	void setPaymentIdAndStatus(String orderId, String paymentId, String status);
	boolean verifyPaymentStatus(String paymentId);
}
