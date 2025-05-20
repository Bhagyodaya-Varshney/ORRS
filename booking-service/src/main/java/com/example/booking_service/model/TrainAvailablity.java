package com.example.booking_service.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TrainAvailablity {
    private TrainAvailableId id;
        
    private int acAvailabilty;
    private int acCancelAvailabilty;
    private String acCancelSeats;
    
    private int slAvailabilty;
    private int slCancelAvailabilty;
    private String slCancelSeats;
}


