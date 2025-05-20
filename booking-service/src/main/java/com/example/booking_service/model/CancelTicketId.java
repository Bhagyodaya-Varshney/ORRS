package com.example.booking_service.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelTicketId {

	 private String trainNumber;
	 private String dateOfJourney;
	 private String coach;

}
