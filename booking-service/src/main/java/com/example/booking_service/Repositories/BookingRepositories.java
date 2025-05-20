package com.example.booking_service.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.booking_service.model.Booking;


@Repository
public interface BookingRepositories extends JpaRepository<Booking, Long>{
	Booking findByPnrNumber(String pnrNumber);
	Booking findByOrderId(String orderId);
}
