package com.example.railway_service.model;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainAvailablity {
    @EmbeddedId
    private TrainAvailableId id;
    
    private int acAvailabilty;
    private int acCancelAvailabilty;
    private String acCancelSeats;
    
    private int slAvailabilty;
    private int slCancelAvailabilty;
    private String slCancelSeats;
}


