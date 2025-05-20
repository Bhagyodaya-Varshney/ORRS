package com.example.admin_service.serviceInterface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@FeignClient(name = "RAILWAY-SERVICE")
public interface TrainClient {
	
	@GetMapping("/train/isExistTrainNumber/{trainNumber}")
	public boolean isTrainNumberAvailable(@PathVariable String trainNumber);
	
	@GetMapping("/train/isExistTrainName/{trainName}")
	public boolean isTrainNameAvailable(@PathVariable String trainName);
	
	@PutMapping("/train/updateTrainNumber/{trainNumber}/{updatedTrainNumber}")
	public boolean trainNumberUpdate(@PathVariable String trainNumber,@PathVariable String updatedTrainNumber);
	
	@PutMapping("/train/updateTrainName/{trainName}/{updatedTrainName}")
	public boolean trainNameUpdate(@PathVariable String trainName,@PathVariable String updatedTrainName);

}
