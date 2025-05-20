package com.example.booking_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainModel {

	 private String trainNumber;
	 private String trainName;
	 private String source;
	 private String destination;
	 private String departureTime;
	 private String arrivalTime;
	 private Integer totalCoach;
	 private Integer noAcCoach;
	 private Integer acFare;
	 private Integer noSlCoach;
	 private Integer slFare;
	 private Double quotaPercent;
	 
	public TrainModel(String trainName, String trainNumber, String source, String destination, String departureTime,
			String arrivalTime, int totalCoach, int noAcCoach, int acFare, int noSlCoach, int slFare, Double quotaPercent) {
		super();
		this.trainName = trainName;
		this.trainNumber = trainNumber;
		this.source = source;
		this.destination = destination;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.totalCoach = totalCoach;
		this.noAcCoach = noAcCoach;
		this.acFare = acFare;
		this.noSlCoach = noSlCoach;
		this.slFare = slFare;
		this.quotaPercent = quotaPercent;
	}
	 
	 
	 

	 
	 
}
