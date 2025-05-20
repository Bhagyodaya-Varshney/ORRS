package com.example.payment_service.services;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.payment_service.model.PaymentModel;
import com.example.payment_service.repositories.PaymentRepo;
import com.example.payment_service.serviceInterface.ServiceInterface;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class Services implements ServiceInterface {

    private String key = "rzp_test_S0r9gRKO9Q56Ag";
    private String secret = "d6lv0gZxtKscjadfv3S1ncL4";
    private int amount;
    
    PaymentModel paymentModel = new PaymentModel();

    @Autowired
    PaymentRepo repo;

    // Process Payment and Generate Order ID
    public String ProcessPayment(int fare) {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(key, secret);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", fare * 100); // Amount in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "receipt_101");

            Order order = razorpayClient.orders.create(orderRequest);
            String razorpayId = order.get("id").toString();
            amount = fare * 100;
            
            
            paymentModel.setOrderId(razorpayId);
            paymentModel.setStatus("PENDING"); // Or "INITIATED", depending on your logic
            paymentModel.setAmount(amount);
//            paymentModel.setPaymentId("");

            // Save the payment model to the repository
            repo.save(paymentModel);

            return razorpayId;
        } catch (Exception e) {
            return null;
        }
    }
    
    public void setPaymentIdAndStatus(String orderId, String paymentId, String status) {
    	PaymentModel pm = repo.findByOrderId(orderId);
    	
    	pm.setPaymentId(paymentId);
    	pm.setStatus(status);
    	
    	repo.save(pm);
    }
    
    public PaymentModel getOrderAmount() {
    	return paymentModel;
    }

    // Verify payment with Razorpay or database
    public boolean verifyPaymentStatus(String paymentId) {
        PaymentModel paymentModel = repo.findByPaymentId(paymentId);
        return paymentModel != null && paymentModel.getStatus().equals("SUCCESS");
    }
}
