package com.example.railway_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainDTO {
	
	@NotBlank(message="Train name is mandatory")
	 private String trainName;
	
	@NotBlank(message="Train number is mandatory")
	@Pattern(regexp = "^\\d{5}$", message = "Train number must be exactly 5 digits and numeric only")
	 private String trainNumber;
	
	@NotBlank(message="Train source is mandatory")
	 private String source;
	
	@NotBlank(message="Train destination is mandatory")
	 private String destination;
	
	@NotBlank(message="Train departure time is mandatory")
	@Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "Time must be in HH:mm format")
	 private String departureTime;
	
	@NotBlank(message="Train arrival time is mandatory")
	@Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "Time must be in HH:mm format")
	 private String arrivalTime;
	
	@NotNull(message="Total Coach is mandatory")
	@Min(value=0, message="Number of Total Coach cannot be negative")
	private Integer totalCoach;
	
	@NotNull(message="AC Coach is mandatory")
	@Min(value=0, message="Number of AC Coach cannot be negative") 
	private Integer noAcCoach;
	
	@NotNull(message="AC Coach Fare is mandatory")
	@Min(value=0, message="Fare of AC Coach cannot be negative") 
	private Integer acFare;
	
	@NotNull(message="Sleeper Coach is mandatory")
	@Min(value=0, message="Number of Sleeper Coach cannot be negative") 
	private Integer noSlCoach;
	
	@NotNull(message="Sleeper Coach Fare is mandatory")
	@Min(value=0, message="Fare of Sleeper Coach cannot be negative") 
	private Integer slFare;
	
	@NotNull(message="Quota Percent is mandatory")
	@Min(value=0, message="Quota Percent cannot be negative") 
	private Double quotaPercent;
	
	@NotNull(message="Train Type is Mandatory to Mention")
	private String trainType;
}
