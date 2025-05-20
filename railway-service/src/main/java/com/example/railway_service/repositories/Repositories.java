package com.example.railway_service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.railway_service.model.TrainModel;

@Repository
public interface Repositories extends JpaRepository<TrainModel, Long> {
    TrainModel findByTrainNumber(String trainNumber);
    List<TrainModel> findByTrainName(String trainName);
    List<TrainModel> findBySourceAndDestination(String source, String destination);
}

