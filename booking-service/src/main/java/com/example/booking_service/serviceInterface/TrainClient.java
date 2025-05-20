package com.example.booking_service.serviceInterface;

import java.util.List;
import com.example.booking_service.model.TrainModel;
import com.example.booking_service.dto.ViewTrainResponse;
import com.example.booking_service.model.TrainAvailablity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "RAILWAY-SERVICE")
public interface TrainClient {
	
	@GetMapping("/train/train/{trainNumber}")
	public TrainModel getTrain(@PathVariable String trainNumber);
	
	@GetMapping("/train/trainAvl/{trainNumber}/{journeyDate}")
	public TrainAvailablity getTrainAvailablity(@PathVariable String trainNumber, @PathVariable String journeyDate);
	
	@GetMapping("/train/getFare/{trainNumber}/{coach}")
	public int findTrainFare(@PathVariable String trainNumber, @PathVariable String coach);
	
	@GetMapping("/train/isSeatAvl/{trainNumber}/{coach}/{NoOfSeat}/{jouneyDate}")
	public boolean isSeatAvl(@PathVariable String trainNumber,@PathVariable String coach,@PathVariable int NoOfSeat,@PathVariable String jouneyDate);
	
	@PostMapping("/train/bookTrain/{trainNumber}/{coach}/{NoOfSeat}/{journeyDate}")
	public boolean bookTrainTicket(@PathVariable String trainNumber,@PathVariable String coach,@PathVariable int NoOfSeat,@PathVariable String journeyDate);
	
	@PostMapping("/train/cancelTkt/{trainNumber}/{coach}/{NoOfSeat}/{journeyDate}/{seatNumbers}")
	public boolean cancelTrainTicket(@PathVariable String trainNumber,@PathVariable String coach,@PathVariable int NoOfSeat,@PathVariable String journeyDate,@PathVariable String seatNumbers);
	
	@GetMapping("/train/viewTicket/{source}/{destination}/{dateOfJourney}")
	public List<ViewTrainResponse> viewTrain(@PathVariable String source, @PathVariable String destination, @PathVariable String dateOfJourney);

}
