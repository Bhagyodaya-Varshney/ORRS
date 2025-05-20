package com.example.booking_service.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CancelTicket")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelTicketModel {

	@EmbeddedId
    private CancelTicketId id;
	
	private Integer numOfTickets;
	private String seatNumbers;
}
