package com.example.booking_service.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketBookingDTO {
	
	@NotEmpty(message = "Passenger list cannot be empty")
	@Size(max=6, message="You can't add more than 6 passengers at a time")
	@Valid
	private List<PassengerDTO> passengers;
	
	@NotBlank(message = "Train ID cannot be null")
	@Size(min = 5, max = 5, message = "Train ID must be exactly 5 digits")
	@Pattern(regexp = "\\d{5}", message = "Train ID must be numeric")
	private String trainId;
	
	@NotBlank(message = "From station cannot be null")
	private String fromStation;
	

	@NotBlank(message = "From station cannot be null")
	private String toStation;
	
	@NotNull(message = "Journey date cannot be null")
	@Future(message = "Journey date must be in the future")
	private LocalDate journeyDate;
	
	@NotBlank(message = "Coach type cannot be null")
	private String coachType;
	
}
