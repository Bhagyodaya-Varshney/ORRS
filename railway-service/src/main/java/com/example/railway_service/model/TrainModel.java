package com.example.railway_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Train")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainModel {

	 @Id
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
	 private String trainType;
	 
}
