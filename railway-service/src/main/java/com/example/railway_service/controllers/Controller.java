package com.example.railway_service.controllers;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.railway_service.dto.ResponseDTO;
import com.example.railway_service.dto.TrainDTO;
import com.example.railway_service.dto.ViewTrainResponse;
import com.example.railway_service.model.TrainAvailablity;
import com.example.railway_service.model.TrainModel;
import com.example.railway_service.serviceInterface.ServiceInterface;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/train")
public class Controller {
	
	@Autowired
	ServiceInterface service;
	
	@PostMapping("/add")
	public ResponseDTO addTrain(@Valid @RequestBody TrainDTO trainModel) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime departure = LocalTime.parse(trainModel.getDepartureTime(), formatter);
		LocalTime arrival = LocalTime.parse(trainModel.getArrivalTime(), formatter);
		
		if (!arrival.isAfter(departure)) {
			throw new IllegalArgumentException("Arrival time must be after departure time");
		}


		return service.addTrain(trainModel);
	}
	
	@DeleteMapping("/delete/{trainNumber}")
	public ResponseDTO deleteTrain(@PathVariable String trainNumber) {
		return service.deleteTrain(trainNumber);
	}
	
	@GetMapping("/isExistTrainNumber/{trainNumber}")
	public boolean isTrainNumberAvailable(@PathVariable String trainNumber) {
		return service.isTrainNumberAvailable(trainNumber);
	}
	
	@GetMapping("/isExistTrainName/{trainName}")
	public boolean isTrainNameAvailable(@PathVariable String trainName) {
		return service.isTrainNameAvailable(trainName);
	}
	
	@GetMapping("/getFare/{trainNumber}/{coach}")
	public int findTrainFare(@PathVariable String trainNumber, @PathVariable String coach) {
		return service.getFare(trainNumber,coach);
	}
	
	@GetMapping("/isSeatAvl/{trainNumber}/{coach}/{NoOfSeat}/{jouneyDate}")
	public boolean isSeatAvl(@PathVariable String trainNumber,@PathVariable String coach,@PathVariable int NoOfSeat,@PathVariable String jouneyDate) {
		return service.avlSeats(trainNumber,coach,NoOfSeat,jouneyDate);
	}
	@GetMapping("train/{trainNumber}")
	public TrainModel getTrain(@PathVariable String trainNumber) {
		return service.getTrainWithTrainNumber(trainNumber);
	}
	
	@GetMapping("trainAvl/{trainNumber}/{journeyDate}")
	public TrainAvailablity getTrainAvailablity(@PathVariable String trainNumber, @PathVariable String journeyDate) {
		return service.getTrainAvailablity(trainNumber,journeyDate);
	}
	
	@PutMapping("/updateTrainNumber/{trainNumber}/{updatedTrainNumber}")
	public boolean trainNumberUpdate(@PathVariable String trainNumber, @PathVariable String updatedTrainNumber) {
		return service.updateTrainNumber(trainNumber,updatedTrainNumber);
	}
	
	@PutMapping("/updateTrainName/{trainName}/{updatedTrainName}")
	public boolean trainNameUpdate(@PathVariable String trainName, @PathVariable String updatedTrainName) {
		return service.updateTrainName(trainName,updatedTrainName);
	}

	@PostMapping("/cancelTkt/{trainNumber}/{coach}/{NoOfSeat}/{journeyDate}/{seatNumbers}")
	public boolean cancelTrainTicket(@PathVariable String trainNumber,@PathVariable String coach,@PathVariable int NoOfSeat,@PathVariable String journeyDate,@PathVariable String seatNumbers) {
		return service.cancelTrainTicket(trainNumber,coach,NoOfSeat,journeyDate,seatNumbers);
	}
	
	@PostMapping("/bookTrain/{trainNumber}/{coach}/{NoOfSeat}/{journeyDate}")
	public boolean bookTrainTicket(@PathVariable String trainNumber,@PathVariable String coach,@PathVariable int NoOfSeat,@PathVariable String journeyDate) {
		return service.bookTrainTicket(trainNumber,coach,NoOfSeat,journeyDate);
	}
	
	@GetMapping("/viewTicket/{source}/{destination}/{dateOfJourney}")
	public List<ViewTrainResponse> viewTrain(@PathVariable String source, @PathVariable String destination, @PathVariable String dateOfJourney){
		return service.viewTrain(source,destination,dateOfJourney);
	}
}

