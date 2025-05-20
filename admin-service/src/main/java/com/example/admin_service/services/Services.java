package com.example.admin_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.admin_service.dto.ResponseDTO;
import com.example.admin_service.exceptions.ResourceNotFoundException;
import com.example.admin_service.exceptions.UpdateFailedException;
import com.example.admin_service.serviceInterface.ServiceInterface;
import com.example.admin_service.serviceInterface.TrainClient;


@Service
public class Services implements ServiceInterface {
	
	@Autowired
	TrainClient trainClient;
	
	public ResponseDTO updateTrainNumber(String trainNumber, String updatedTrainNumber)throws ResourceNotFoundException  {
		
		ResponseDTO res = new ResponseDTO();
		boolean isTrainExists = trainClient.isTrainNumberAvailable(trainNumber);
		if(!isTrainExists) {
			throw new ResourceNotFoundException("Train Does Not Exists");
		}
		
		boolean trainNumberUpdate = trainClient.trainNumberUpdate(trainNumber,updatedTrainNumber);
		if(!trainNumberUpdate) {
			throw new UpdateFailedException("Unable to Update the Train Details");
		}
		res.setRes("Train Details Updated");
		return res;
	}
	
	public ResponseDTO updateTrainName(String trainName, String updatedTrainName) {
		
		ResponseDTO res = new ResponseDTO();
		
		boolean isTrainExists = trainClient.isTrainNameAvailable(trainName);
		if(!isTrainExists) {
			res.setRes("Train Does Not Exists");
			return res;
		}
		
		boolean trainNumberUpdate = trainClient.trainNameUpdate(trainName,updatedTrainName);
		if(!trainNumberUpdate) {
			res.setRes("Unable to Update the Train Details");
			return res;
		}
		res.setRes("Train Details Updated");
		return res;
	}
	
}
