package com.example.admin_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.admin_service.dto.ResponseDTO;
import com.example.admin_service.exceptions.ResourceNotFoundException;
import com.example.admin_service.serviceInterface.ServiceInterface;

import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/admin")
public class Controller {

    @Autowired
    ServiceInterface service;

    @GetMapping("/greet")
    public String greet() {
        return "Admin Dashboard";
    }

    @PutMapping("/updateTrainNumber/{trainNumber}/{updatedTrainNumber}")
    @CircuitBreaker(name = "Admin-Service", fallbackMethod = "updateTrainDetailsFallBackMethod")
    public ResponseDTO updateTrainNumber(@PathVariable String trainNumber, @PathVariable String updatedTrainNumber) throws RetryableException{
        return service.updateTrainNumber(trainNumber, updatedTrainNumber);
    }

    public ResponseDTO updateTrainDetailsFallBackMethod(RetryableException ex) {
        ResponseDTO res = new ResponseDTO();
        res.setRes("Service is not Available");
        return res;
    }

    @PutMapping("/updateTrainName/{trainName}/{updatedTrainName}")
    @CircuitBreaker(name = "Admin-Service", fallbackMethod = "updateTrainDetailsFallBackMethod")
    public ResponseDTO updateTrainName(@PathVariable String trainName, @PathVariable String updatedTrainName) throws Exception {
        return service.updateTrainName(trainName, updatedTrainName);
    }
}
