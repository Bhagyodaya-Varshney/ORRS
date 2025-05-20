package com.example.booking_service.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerModel {

	private String passengerName;
	private String passengerAge;
	private String passengerGender;
	private Integer fare;
	private String seatNumber;
	
	public PassengerModel(String passengerName, String passengerAge, String passengerGender, Integer fare) {
		super();
		this.passengerName = passengerName;
		this.passengerAge = passengerAge;
		this.passengerGender = passengerGender;
		this.fare = fare;
	}
	
}
