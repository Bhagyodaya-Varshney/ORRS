package com.example.booking_service.serviceInterface;

import java.util.List;

import com.example.booking_service.dto.TicketBookingDTO;
import com.example.booking_service.dto.TicketResponse;
import com.example.booking_service.dto.ViewTrainResponse;
import com.example.booking_service.model.PassengerModel;
import com.example.booking_service.model.TrainAvailablity;
import com.example.booking_service.model.TrainModel;

public interface ServiceInterface {
	String bookTicket(TicketBookingDTO bookingRequest);
	TicketResponse confirmBooking(String paymentId,String orderId);
	String cancelTicket(String pnrNumber);
	String generatePNRNumber();
	String generateSeatNumber(String coach,TrainAvailablity avl,TrainModel train, int noOfSeats, List<PassengerModel> newPassengerList);
	TicketResponse viewTicket(String pnrNumber);
	List<ViewTrainResponse> viewTrain(String source, String destination, String dateOfJourney);
}
