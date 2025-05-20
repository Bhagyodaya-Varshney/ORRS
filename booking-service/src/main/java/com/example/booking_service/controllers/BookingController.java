package com.example.booking_service.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking_service.dto.TicketBookingDTO;
import com.example.booking_service.dto.TicketResponse;
import com.example.booking_service.dto.ViewTrainResponse;
import com.example.booking_service.serviceInterface.ServiceInterface;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookingController {
	
	
	@Autowired
	ServiceInterface service;
	
	@PostMapping("/bookTicket")
//	@CircuitBreaker(name = "Railway-Service", fallbackMethod = "viewTicketBookTicketFallBackMethod")
	public String bookTicket(@RequestBody @Valid TicketBookingDTO bookingRequest) throws Exception{
		return service.bookTicket(bookingRequest);
	}
	
	@PostMapping("/confirmBooking/{paymentId}/{orderId}")
	public TicketResponse confirmBooking(@PathVariable String paymentId, @PathVariable String orderId) {
	    // Call service to confirm booking
	    return service.confirmBooking(paymentId,orderId);
	}
	
	@GetMapping("/viewTicket/{pnrNumber}")
	@CircuitBreaker(name = "Railway-Service", fallbackMethod = "viewTicketBookTicketFallBackMethod")
	public TicketResponse viewTicket(@PathVariable String pnrNumber) throws Exception{
		return service.viewTicket(pnrNumber);
	}
	
	public TicketResponse viewTicketBookTicketFallBackMethod(FeignException ex){
		TicketResponse res = new TicketResponse();
		res.setBookingStatus("Service Not Available");
		return res;
	}
	
	@PostMapping("/cancelTicket/{pnrNumber}")
	@CircuitBreaker(name = "Railway-Service", fallbackMethod = "cancelTicketFallBackMethod")
	public String cancelTicket(@PathVariable String pnrNumber) throws Exception{
		return service.cancelTicket(pnrNumber);
	}
	
	public String cancelTicketFallBackMethod(Exception ex){
		return "Service Not Available";
	}
	
	@GetMapping("/viewTrain/{source}/{destination}/{dateOfJourney}")
	@CircuitBreaker(name = "Railway-Service", fallbackMethod = "viewTrainFallBackMethod")
	public List<ViewTrainResponse> viewTrain(@PathVariable String source, @PathVariable String destination, @PathVariable String dateOfJourney) throws Exception{
		return service.viewTrain(source,destination,dateOfJourney);
	}
	
	public List<ViewTrainResponse> viewTrainFallBackMethod(Exception ex){
		return new ArrayList<ViewTrainResponse>();
	}
}
