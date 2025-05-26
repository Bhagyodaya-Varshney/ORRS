package com.example.booking_service.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String userEmail;
	
	private String trainId;
	private String fromStation;
	private String toStation;
	private LocalDate journeyDate;
	private String coachType;
	private Integer noOfTickets;
	private Integer totalFare;
	private String bookingStatus;
	private LocalDateTime bookingTime;
	private String pnrNumber;
	private String seatNumber;
	private String orderId;
	
	@ElementCollection
	private List<PassengerModel> passengers;
	
	
	
}
