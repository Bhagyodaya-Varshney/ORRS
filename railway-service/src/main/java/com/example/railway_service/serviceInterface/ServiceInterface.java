package com.example.railway_service.serviceInterface;

import java.util.List;
import com.example.railway_service.dto.ResponseDTO;
import com.example.railway_service.dto.TrainDTO;
import com.example.railway_service.dto.ViewTrainResponse;
import com.example.railway_service.model.TrainAvailablity;
import com.example.railway_service.model.TrainModel;

public interface ServiceInterface {
	
	
	ResponseDTO addTrain(TrainDTO train);
	ResponseDTO deleteTrain(String trainNumber);
	boolean isTrainNumberAvailable(String trainNumber);
	boolean isTrainNameAvailable(String trainName);
	boolean updateTrainNumber(String trainNumber, String updatedTrainNumber);
	boolean updateTrainName(String trainName, String updatedTrainName);
	int getFare(String trainNumber, String coach);
	boolean avlSeats(String trainNumber, String coach, int seatAvl, String journeyDate);
	boolean bookTrainTicket(String trainNumber, String coach, int NoOfSeat, String journeyDate);
	TrainModel getTrainWithTrainNumber(String trainNumber);
	TrainAvailablity getTrainAvailablity(String trainNumber, String journeyDate);
	boolean cancelTrainTicket(String trainNumber, String coach, int NoOfSeat, String journeyDate, String seatNumbers);
	List<ViewTrainResponse> viewTrain(String source, String destination, String dateOfJourney);
}
