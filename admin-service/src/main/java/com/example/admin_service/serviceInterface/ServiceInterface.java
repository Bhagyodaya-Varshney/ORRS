package com.example.admin_service.serviceInterface;

import com.example.admin_service.dto.ResponseDTO;

public interface ServiceInterface {
	ResponseDTO updateTrainNumber(String trainNumber, String updatedTrainNumber);
	ResponseDTO updateTrainName(String trainName, String updatedTrainName);
}
