package com.example.railway_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.railway_service.dto.ResponseDTO;
import com.example.railway_service.dto.TrainDTO;
import com.example.railway_service.model.TrainAvailableId;
import com.example.railway_service.model.TrainAvailablity;
import com.example.railway_service.model.TrainModel;
import com.example.railway_service.repositories.AvlTicketRepository;
import com.example.railway_service.repositories.Repositories;

public class ServicesTest {

    private Services services;
    private Repositories repo;
    private AvlTicketRepository avlRepo;

    @BeforeEach
    public void setUp() {
        repo = mock(Repositories.class);
        avlRepo = mock(AvlTicketRepository.class);
        services = new Services();
        services.repo = repo;
        services.avlRepo = avlRepo;
    }

    @Test
    public void testAddTrain_Success() {
        TrainDTO trainDTO = new TrainDTO("12345", "Express", "Mumbai", "Delhi", "10:00", "18:00", 10, 5, 1000, 5, 500, 10.0,"Express");
        TrainModel trainModel = new TrainModel("12345", "Express", "Mumbai", "Delhi", "10:00", "18:00", 10, 5, 1000, 5, 500, 10.0,"Espress");
        
        when(repo.findByTrainNumber(trainDTO.getTrainNumber())).thenReturn(null);
        when(repo.save(trainModel)).thenReturn(trainModel);

        ResponseDTO response = services.addTrain(trainDTO);

        assertEquals("Train Added Successfully", response.getRes());
    }

    @Test
    public void testAddTrain_TrainAlreadyExists() {
        TrainDTO trainDTO = new TrainDTO("12345", "Express", "Mumbai", "Delhi", "10:00", "18:00", 10, 5, 1000, 5, 500, 10.0,"Espress");
        TrainModel trainModel = new TrainModel("12345", "Express", "Mumbai", "Delhi", "10:00", "18:00", 10, 5, 1000, 5, 500, 10.0,"Espress");
        
        when(repo.findByTrainNumber(trainDTO.getTrainNumber())).thenReturn(trainModel);

        ResponseDTO response = services.addTrain(trainDTO);

        assertEquals("Train Already Exists", response.getRes());
    }

    @Test
    public void testDeleteTrain_Success() {
        TrainModel trainModel = new TrainModel("12345", "Express", "Mumbai", "Delhi", "10:00", "18:00", 10, 5, 1000, 5, 500, 10.0,"Espress");
        
        when(repo.findByTrainNumber("12345")).thenReturn(trainModel);

        ResponseDTO response = services.deleteTrain("12345");

        assertEquals("Deleted Successfully", response.getRes());
    }

    @Test
    public void testDeleteTrain_TrainDoesNotExist() {
        when(repo.findByTrainNumber("12345")).thenReturn(null);

        ResponseDTO response = services.deleteTrain("12345");

        assertEquals("Train Does Not Exists", response.getRes());
    }

    @Test
    public void testIsTrainNumberAvailable_True() {
        TrainModel trainModel = new TrainModel("12345", "Express", "Mumbai", "Delhi", "10:00", "18:00", 10, 5, 1000, 5, 500, 10.0,"Espress");
        
        when(repo.findByTrainNumber("12345")).thenReturn(trainModel);

        boolean result = services.isTrainNumberAvailable("12345");

        assertEquals(true, result);
    }

    @Test
    public void testIsTrainNumberAvailable_False() {
        when(repo.findByTrainNumber("12345")).thenReturn(null);

        boolean result = services.isTrainNumberAvailable("12345");

        assertEquals(false, result);
    }

    @Test
    public void testGetFare_Success() {
        TrainModel trainModel = new TrainModel("12345", "Express", "Mumbai", "Delhi", "10:00", "18:00", 10, 5, 1000, 5, 500, 10.0,"Espress");
        
        when(repo.findByTrainNumber("12345")).thenReturn(trainModel);

        int fare = services.getFare("12345", "AC");

        assertEquals(1000, fare);
    }

    @Test
    public void testGetFare_TrainNotFound() {
        when(repo.findByTrainNumber("12345")).thenReturn(null);

        int fare = services.getFare("12345", "AC");

        assertEquals(-1, fare);
    }

    @Test
    public void testAvlSeats_Success() {
        TrainAvailablity avl = new TrainAvailablity();
        avl.setAcAvailabilty(50);
        avl.setSlAvailabilty(100);
        
        when(avlRepo.findById_TrainNumberAndId_DateOfJourney("12345", "2025-05-05")).thenReturn(avl);

        boolean result = services.avlSeats("12345", "AC", 10, "2025-05-05");

        assertEquals(true, result);
    }

    @Test
    public void testAvlSeats_NotEnoughSeats() {
        TrainAvailablity avl = new TrainAvailablity();
        avl.setAcAvailabilty(5);
        avl.setSlAvailabilty(100);
        
        when(avlRepo.findById_TrainNumberAndId_DateOfJourney("12345", "2025-05-05")).thenReturn(avl);

        boolean result = services.avlSeats("12345", "AC", 10, "2025-05-05");

        assertEquals(false, result);
    }

    @Test
    public void testBookTrainTicket_Success() {
        TrainAvailablity avl = new TrainAvailablity();
        avl.setAcAvailabilty(50);
        avl.setSlAvailabilty(100);
        
        when(avlRepo.findById_TrainNumberAndId_DateOfJourney("12345", "2025-05-05")).thenReturn(avl);

        boolean result = services.bookTrainTicket("12345", "AC", 10, "2025-05-05");

        assertEquals(true, result);
    }

    @Test
    public void testCancelTrainTicket_Success() {
        TrainAvailablity avl = new TrainAvailablity();
        avl.setAcAvailabilty(50);
        avl.setSlAvailabilty(100);
        
        when(avlRepo.findById_TrainNumberAndId_DateOfJourney("12345", "2025-05-05")).thenReturn(avl);

        boolean result = services.cancelTrainTicket("12345", "AC", 10, "2025-05-05","");

        assertEquals(true, result);
    }
}
