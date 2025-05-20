package com.example.booking_service.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.booking_service.model.CancelTicketId;
import com.example.booking_service.model.CancelTicketModel;

public interface CancelTicketRepo extends JpaRepository<CancelTicketModel,CancelTicketId>{
	
}
