package com.example.booking_service.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.booking_service.Repositories.BookingRepositories;
import com.example.booking_service.Repositories.CancelTicketRepo;
import com.example.booking_service.dto.PassengerDTO;
import com.example.booking_service.dto.TicketBookingDTO;
import com.example.booking_service.dto.TicketResponse;
import com.example.booking_service.dto.ViewTrainResponse;
import com.example.booking_service.exceptions.CancelTicketFailedException;
import com.example.booking_service.exceptions.SeatNotAvailableException;
import com.example.booking_service.exceptions.TicketNotFoundException;
import com.example.booking_service.model.Booking;
import com.example.booking_service.model.CancelTicketId;
import com.example.booking_service.model.CancelTicketModel;
import com.example.booking_service.model.PassengerModel;
import com.example.booking_service.model.TrainModel;
import com.example.booking_service.model.TrainAvailablity;
import com.example.booking_service.serviceInterface.PaymentClient;
import com.example.booking_service.serviceInterface.ServiceInterface;
import com.example.booking_service.serviceInterface.TrainClient;


@Service
public class Services implements ServiceInterface{

	@Autowired
	BookingRepositories repo;
	
	@Autowired
	CancelTicketRepo cRepo;
	
	@Autowired
	TrainClient trainClient;
	
	@Autowired
	PaymentClient paymentClient;
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	
		
	
	public String bookTicket(TicketBookingDTO bookingRequest) {
		
		Booking booking = new Booking();
		boolean seatAvl = trainClient.isSeatAvl(
				bookingRequest.getTrainId(),
        		bookingRequest.getCoachType(),
        		bookingRequest.getPassengers().size(),
        		bookingRequest.getJourneyDate().format(formatter)
		);

		if (!seatAvl) {
			throw new SeatNotAvailableException("Sorry Seats are Not Available!!");
		}

		TrainModel train = trainClient.getTrain(bookingRequest.getTrainId());
		TicketResponse res = new TicketResponse();

		int fare = trainClient.findTrainFare(
				bookingRequest.getTrainId(),
				bookingRequest.getCoachType()
		);

    	double quotaPercent = 1.0 - train.getQuotaPercent() / 100.0;
    	int noOfSeats = bookingRequest.getPassengers().size();

    	// Create Booking object
    	booking.setBookingStatus("NOT CONFIRMED");
    	booking.setBookingTime(LocalDateTime.now());
    	booking.setTrainId(bookingRequest.getTrainId());
    	booking.setFromStation(bookingRequest.getFromStation());
    	booking.setToStation(bookingRequest.getToStation());
    	booking.setJourneyDate(bookingRequest.getJourneyDate());
    	booking.setCoachType(bookingRequest.getCoachType());
    	booking.setNoOfTickets(noOfSeats);

    	// Calculate fare per passenger
    	List<PassengerDTO> passengerList = bookingRequest.getPassengers();
    	List<PassengerModel> newPassengerList = new ArrayList<>();
    	int totalFare = 0;

    	for (PassengerDTO p : passengerList) {
    		int thisFare = p.getPassengerGender().equalsIgnoreCase("Female") || p.getPassengerAge() >= 60
    				? (int) (fare * quotaPercent)
    						: fare;

    		totalFare += thisFare;

    		PassengerModel pm = new PassengerModel(
    				p.getPassengerName(),
    				p.getPassengerAge().toString(),
    				p.getPassengerGender(),
    				thisFare
    		);

    		newPassengerList.add(pm);
    	}
    	booking.setTotalFare(totalFare);
    	booking.setPassengers(newPassengerList);


    // Step 1: Generate payment order
    	String paymentOrderId = paymentClient.generateOrder(totalFare);
    	if (paymentOrderId == null) {
    		throw new RuntimeException("Failed to generate payment order. Try again later.");
    	}
    	booking.setOrderId(paymentOrderId);
    	repo.save(booking);
    	return "Please Proceed for the Payment for Confirming the Ticket... And Your OrderId is "+paymentOrderId;
	}
	
	public TicketResponse confirmBooking(String paymentId,String orderId) {
	    // Step 1: Verify payment using paymentId
	    boolean isPaymentSuccess = paymentClient.verifyPayment(paymentId);

	    if (!isPaymentSuccess) {
	        throw new RuntimeException("Payment failed! Could not confirm booking.");
	    }
	    
	    Booking findBooking = repo.findByOrderId(orderId);
	    
		TrainModel train = trainClient.getTrain(findBooking.getTrainId());
		

		findBooking.setBookingStatus("CONFIRMED");
		findBooking.setPnrNumber(generatePNRNumber());
		
		TrainAvailablity avl = trainClient.getTrainAvailablity(findBooking.getTrainId(),findBooking.getJourneyDate().format(formatter));

		String seatNumbers = generateSeatNumber(
				findBooking.getCoachType(), avl, train, findBooking.getNoOfTickets(), findBooking.getPassengers()
		);
		findBooking.setSeatNumber(seatNumbers);
	    repo.save(findBooking);  // Save the updated booking status
	    
	    trainClient.bookTrainTicket(findBooking.getTrainId(),
	    		findBooking.getCoachType(),
	    		findBooking.getPassengers().size(),
	    		findBooking.getJourneyDate().format(formatter));

	    // Prepare response
	    TicketResponse res = new TicketResponse();
	    res.setBookingStatus("CONFIRMED");
	    res.setCoachType(findBooking.getCoachType());
	    res.setFromStation(findBooking.getFromStation());
	    res.setToStation(findBooking.getToStation());
	    res.setPnrNumber(findBooking.getPnrNumber());
	    res.setBookingDate(findBooking.getBookingTime().format(formatter));
		res.setJourneyDate(findBooking.getJourneyDate().format(formatter));
	    res.setSeatNumbers(findBooking.getSeatNumber());
	    res.setTotalFare(findBooking.getTotalFare());
	    res.setPassengers(findBooking.getPassengers());
	    res.setTrainNumber(findBooking.getTrainId());
		res.setTrainName(train.getTrainName());
	    
	    return res;
	}

	
	public String generatePNRNumber() {
		Random r = new Random();
		return "PNR"+ (1000000 + r.nextInt(9000000));
	}
	
	public String generateSeatNumber(String coach,TrainAvailablity avl,TrainModel train, int noOfSeats, List<PassengerModel> newPassengerList) {
		StringBuilder sb = new StringBuilder();
		
		if(coach.equals("AC") && avl.getAcAvailabilty()!=0) {
			int totalSeat = (train.getNoAcCoach()*72)-avl.getAcAvailabilty();
			int firstSeat = totalSeat+1;
			for(int i=0;i<noOfSeats;i++) {
				String seat = "AC-"+(firstSeat+i)+",";
				sb.append(seat);
				newPassengerList.get(i).setSeatNumber(seat);
			}
			return sb.toString();
		}
		else if(coach.equals("AC") && avl.getAcCancelAvailabilty()==0 && avl.getAcCancelAvailabilty()!=0) {
			String[] seatNumbers = avl.getAcCancelSeats().split(",");
			for(int i=0;i<noOfSeats;i++) {
				sb.append(seatNumbers[i]);
				newPassengerList.get(i).setSeatNumber(seatNumbers[i]);
			}
			
			return sb.toString();
		}
		else if(coach.equals("AC") && (avl.getAcAvailabilty() < noOfSeats) && avl.getAcCancelAvailabilty() != 0) {
			String[] seatNumbers = avl.getAcCancelSeats().split(",");
			int firstSeat = avl.getAcAvailabilty()+1;
			for(int i=0;i<avl.getAcAvailabilty();i++) {
				String seat = "AC-"+(firstSeat+i)+",";
				sb.append(seat);
				newPassengerList.get(i).setSeatNumber(seat);
			}
			for(int i=noOfSeats-avl.getAcAvailabilty();i<noOfSeats;i++) {
				sb.append(seatNumbers[i]);
				newPassengerList.get(i).setSeatNumber(seatNumbers[i]);
			}
			
			return sb.toString();
		}
		else {
			int totalSeat = (train.getNoSlCoach()*72)-avl.getSlAvailabilty();
			int firstSeat = totalSeat+1;
			for(int i=0;i<noOfSeats;i++) {
				String seat = "SL-"+(firstSeat+i)+",";
				sb.append(seat);
				newPassengerList.get(i).setSeatNumber(seat);
			}
			return sb.toString();
		}
	}
	
	public String cancelTicket(String pnrNumber) {
		System.out.println(1);
		Booking tkt = repo.findByPnrNumber(pnrNumber);
		if(tkt == null) {
			throw new TicketNotFoundException("Booking Not Found for the Given PNR Number");
		}
		System.out.println(1);
		boolean isTktCancel = trainClient.cancelTrainTicket(tkt.getTrainId(), tkt.getCoachType(), tkt.getNoOfTickets(), tkt.getJourneyDate().format(formatter),tkt.getSeatNumber());
		if(!isTktCancel) {
			throw new CancelTicketFailedException("Unable to Cancel Ticket Right Now!! Try Again Later");
		}
		System.out.println(1);
		repo.delete(tkt);
		return "Ticket with PNR Number:"+pnrNumber+"Cancelled Successfully";
	}
	
	public TicketResponse viewTicket(String pnrNumber) {
		Booking tkt = repo.findByPnrNumber(pnrNumber);
		
		TicketResponse res = new TicketResponse();
		TrainModel train = trainClient.getTrain(tkt.getTrainId());

		
		res.setBookingDate(tkt.getBookingTime().format(formatter));
		res.setBookingStatus(tkt.getBookingStatus());
		res.setCoachType(tkt.getCoachType());
		res.setFromStation(tkt.getFromStation());
		res.setJourneyDate(tkt.getJourneyDate().format(formatter));
		res.setPnrNumber(tkt.getPnrNumber());
		res.setSeatNumbers(tkt.getSeatNumber());
		res.setToStation(tkt.getToStation());
		res.setTotalFare(tkt.getTotalFare());
		res.setTrainNumber(tkt.getTrainId());
		res.setPassengers(tkt.getPassengers());
		res.setTrainName(train.getTrainName());
		
		return res;
		
	}
	
	public List<ViewTrainResponse> viewTrain(String source, String destination, String dateOfJourney){
		List<ViewTrainResponse> trainList = trainClient.viewTrain(source,destination,dateOfJourney);
		return trainList;
	}
}
