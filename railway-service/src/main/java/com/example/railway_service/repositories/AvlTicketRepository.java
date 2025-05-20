package com.example.railway_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.railway_service.model.TrainAvailableId;
import com.example.railway_service.model.TrainAvailablity;

@Repository
public interface AvlTicketRepository extends JpaRepository<TrainAvailablity, TrainAvailableId> {
	
    TrainAvailablity findById_TrainNumberAndId_DateOfJourney(String trainNumber, String journeyDate);
}


