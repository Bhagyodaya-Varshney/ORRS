package com.example.booking_service.dto;

import java.util.List;
import com.example.booking_service.model.PassengerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
	private String bookingStatus;
	private String coachType;
	private String fromStation;
	private String toStation;
	private String pnrNumber;
	private String bookingDate;
	private String journeyDate;
	private String seatNumbers;
	private Integer totalFare;
	private String trainNumber;
	private String trainName;
	private List<PassengerModel> passengers;
	
	
	public TicketResponse(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	
	
}
