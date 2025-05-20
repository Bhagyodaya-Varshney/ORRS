package com.example.booking_service.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
public class PaymentModel {
	private Long id;
	private String trainId;
	private String pnrNumber;
	private Integer amount;
	private String razorpayOrderId;
	private String status;
	private LocalDateTime paymentTime;
}
