package com.example.booking_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PassengerDTO {
    @NotBlank(message = "Passenger name cannot be blank")
    private String passengerName;

    @NotNull(message = "Passenger age cannot be null")
    @Positive(message = "Passenger age must be a positive number")
    private Integer passengerAge;

    @NotBlank(message = "Passenger gender cannot be blank")
    @Pattern(regexp = "Male|Female|Other", message = "Passenger gender must be Male, Female, or Other")
    private String passengerGender;
}

