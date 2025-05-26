package com.example.railway_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewTrainResponse {

	private String trainName;
	private String trainNumber;
	private String source;
	private String departureTime;
	private String destination;
	private String arrivalTime;
	private Integer acAvl;
	private Integer acFare;
	private Integer slAvl;
	private Integer slFare;
	private String trainType;
}
