package com.example.railway_service.services;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.railway_service.dto.ResponseDTO;
import com.example.railway_service.dto.TrainDTO;
import com.example.railway_service.dto.ViewTrainResponse;
import com.example.railway_service.exceptions.InvalidCoachException;
import com.example.railway_service.exceptions.TrainAlreadyExistsException;
import com.example.railway_service.exceptions.TrainNotFoundException;
import com.example.railway_service.model.TrainAvailableId;
import com.example.railway_service.model.TrainAvailablity;
import com.example.railway_service.model.TrainModel;
import com.example.railway_service.repositories.AvlTicketRepository;
import com.example.railway_service.repositories.Repositories;
import com.example.railway_service.serviceInterface.ServiceInterface;


@Service
public class Services implements ServiceInterface{
	
	@Autowired
	Repositories repo;
	
	@Autowired
	AvlTicketRepository avlRepo;
	
	public ResponseDTO addTrain(TrainDTO train) {
		ResponseDTO res = new ResponseDTO();
		if((repo.findByTrainNumber(train.getTrainNumber()) == null) && (train.getNoAcCoach()+train.getNoSlCoach()==train.getTotalCoach())) {
			TrainModel addTrain = new TrainModel(train.getTrainNumber(),train.getTrainName(),train.getSource(),train.getDestination(),train.getDepartureTime(),train.getArrivalTime(),train.getTotalCoach(),train.getNoAcCoach(),train.getAcFare(),train.getNoSlCoach(),train.getSlFare(),train.getQuotaPercent(),train.getTrainType());
			repo.save(addTrain);
			res.setRes("Train Added Successfully");
			return res;
		}
		throw new TrainAlreadyExistsException("Train Already Exists");
	}
	
	public ResponseDTO deleteTrain(String trainNumber) {
		ResponseDTO res = new ResponseDTO();
		if(repo.findByTrainNumber(trainNumber) == null){
			res.setRes("Train Does Not Exists");
			return res;
		}
		repo.delete(repo.findByTrainNumber(trainNumber));
		res.setRes("Deleted Successfully");
		return res;
	}
	
	public boolean isTrainNumberAvailable(String trainNumber) {
		if(repo.findByTrainNumber(trainNumber) != null) {
			return true;
		}
		return false;
	}
	
	public boolean isTrainNameAvailable(String trainName) {
	    if (repo.findByTrainName(trainName) != null) {
	        return true;
	    }
	    return false;
	}
	
	public boolean updateTrainNumber(String trainNumber, String updatedTrainNumber) {
		TrainModel train = repo.findByTrainNumber(trainNumber);
		if(train == null) {
			throw new TrainNotFoundException("Train Not Found");
		}
		train.setTrainNumber(updatedTrainNumber);
		repo.save(train);
		return true;
	}
	
	public boolean updateTrainName(String trainName, String updatedTrainName) {
		List<TrainModel> trainList = repo.findByTrainName(trainName);
		if(trainList == null) {
			throw new TrainNotFoundException("Train Not Found");
		}
		for(TrainModel tm : trainList) {
			tm.setTrainName(updatedTrainName);
			repo.save(tm);
		}
		return true;
	}
	
	public int getFare(String trainNumber, String coach) {
		TrainModel train = repo.findByTrainNumber(trainNumber);
		if(train==null) {
			throw new TrainNotFoundException("Train Not Found");
		}
		return "SL".equals(coach)?train.getSlFare():train.getAcFare();	
	}
	
	public boolean avlSeats(String trainNumber, String coach, int NoOfSeat, String journeyDate) {
	    TrainAvailablity avl = avlRepo.findById_TrainNumberAndId_DateOfJourney(trainNumber, journeyDate);
	    
	    if (avl == null) {
	        return true;
	    }
	    if ("AC".equals(coach)) {
	        if (avl.getAcAvailabilty() == 0 || (avl.getAcAvailabilty()+avl.getAcCancelAvailabilty()) < NoOfSeat) {
	            return false;
	        }
	    } else if ("SL".equals(coach)) {
	        if (avl.getSlAvailabilty() == 0 || (avl.getSlAvailabilty()+avl.getSlCancelAvailabilty()) < NoOfSeat) {
	            return false;
	        }
	    }
	    else {
	    	throw new InvalidCoachException("Invalid Coach Type");
	    }
	    return true;
	}

	
	public boolean bookTrainTicket(String trainNumber, String coach, int NoOfSeat, String journeyDate) {
	    TrainAvailablity avl = avlRepo.findById_TrainNumberAndId_DateOfJourney(trainNumber, journeyDate);
		if(avl == null) {
			TrainModel train = repo.findByTrainNumber(trainNumber);
			TrainAvailablity newTkt = new TrainAvailablity();
			TrainAvailableId id = new TrainAvailableId();
			
			id.setTrainNumber(trainNumber);
			id.setDateOfJourney(journeyDate);
			newTkt.setId(id);
			
			if(coach.equals("AC")) {
				newTkt.setAcAvailabilty((train.getNoAcCoach()*72)-NoOfSeat);
				newTkt.setSlAvailabilty(train.getNoSlCoach()*72);
			}
			else if(coach.equals("SL")) {
				newTkt.setAcAvailabilty(train.getNoAcCoach()*72);
				newTkt.setSlAvailabilty((train.getNoSlCoach()*72)-NoOfSeat);
			}
			else {
				throw new InvalidCoachException("Invalid Coach Type");
			}
			newTkt.setAcCancelAvailabilty(0);
			newTkt.setSlCancelAvailabilty(0);
			newTkt.setAcCancelSeats("");
			newTkt.setSlCancelSeats("");
			
			avlRepo.save(newTkt);
			return true;
		}
		else {
			if(coach.equals("AC")) {
				avl.setAcAvailabilty(avl.getAcAvailabilty()-NoOfSeat);
			}
			else if(coach.equals("SL")) {
				avl.setSlAvailabilty(avl.getSlAvailabilty()-NoOfSeat);
			}
			else {
				throw new InvalidCoachException("Invalid Coach Type");
			}
			
			avlRepo.save(avl);
			return true;
		}
	}
	
	public boolean cancelTrainTicket(String trainNumber, String coach, int NoOfSeat, String journeyDate, String seatNumbers) {
		TrainAvailablity avl = avlRepo.findById_TrainNumberAndId_DateOfJourney(trainNumber, journeyDate);
		if(avl!=null) {
			if(coach.equals("AC")) {
				avl.setAcCancelAvailabilty(NoOfSeat+avl.getAcCancelAvailabilty());
				avl.setAcCancelSeats(avl.getAcCancelSeats()+seatNumbers);
			}
			else {
				avl.setSlCancelAvailabilty(NoOfSeat+avl.getSlCancelAvailabilty());
				avl.setSlCancelSeats(avl.getSlCancelSeats()+seatNumbers);
			}
			avlRepo.save(avl);
			return true;
		}
		throw new TrainNotFoundException("Train Not Found");
	}
	
	public TrainModel getTrainWithTrainNumber(String trainNumber) {
		TrainModel train = repo.findByTrainNumber(trainNumber);
        if (train == null) {
            throw new TrainNotFoundException("Train Not Found");
        }
        return train;
	}
	
	public TrainAvailablity getTrainAvailablity(String trainNumber, String journeyDate) {
		
		TrainAvailablity avl = avlRepo.findById_TrainNumberAndId_DateOfJourney(trainNumber,journeyDate);
		TrainModel train = repo.findByTrainNumber(trainNumber);
		
		if(avl == null) {
			
			TrainAvailableId id = new TrainAvailableId();
			id.setTrainNumber(trainNumber);
			id.setDateOfJourney(journeyDate);
			TrainAvailablity ta = new TrainAvailablity();
			ta.setId(id);
			ta.setAcAvailabilty(train.getNoAcCoach()*72);
			ta.setSlAvailabilty(train.getNoSlCoach()*72);
			ta.setAcCancelAvailabilty(0);
			ta.setSlCancelAvailabilty(0);
			
			return ta;
		}
		return avl;
	}
	
	public List<ViewTrainResponse> viewTrain(String source, String destination, String dateOfJourney){
		List<TrainModel> trainList = repo.findBySourceAndDestination(source,destination);
		List<ViewTrainResponse> returnList = new ArrayList<>();
		
		for(TrainModel tm : trainList) {
			TrainAvailablity avl = getTrainAvailablity(tm.getTrainNumber(),dateOfJourney);
			System.out.println(avl.getAcAvailabilty());
			returnList.add(new ViewTrainResponse(tm.getTrainName(),tm.getTrainNumber(),tm.getSource(),tm.getDepartureTime(),tm.getDestination(),tm.getArrivalTime(),avl.getAcAvailabilty(),tm.getAcFare(),avl.getSlAvailabilty(),tm.getSlFare()));
		}
		
		return returnList;
	}
	
	
}
