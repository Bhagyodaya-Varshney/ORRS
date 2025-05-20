package com.example.booking_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

	private Long id;
	
	private String pnrNumber;
	private String trainNumber;
	private String seatNumber;
	private String journeyDate;
	
}
