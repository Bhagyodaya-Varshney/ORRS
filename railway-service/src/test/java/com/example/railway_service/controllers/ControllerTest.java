package com.example.railway_service.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.railway_service.dto.ResponseDTO;
import com.example.railway_service.dto.TrainDTO;
import com.example.railway_service.model.TrainAvailablity;
import com.example.railway_service.model.TrainModel;
import com.example.railway_service.serviceInterface.ServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ControllerTest {

    private MockMvc mockMvc;
    private ServiceInterface service;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        service = mock(ServiceInterface.class);
        Controller controller = new Controller();
        controller.service = service;
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddTrain() throws Exception {
        TrainDTO trainDTO = new TrainDTO("12345", "Express", "Mumbai", "Delhi", "10:00", "18:00", 10, 5, 1000, 5, 500, 10.0,"Espress");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setRes("Train Added Successfully");

        when(service.addTrain(trainDTO)).thenReturn(responseDTO);

        String trainDTOJson = objectMapper.writeValueAsString(trainDTO);
        String responseDTOJson = objectMapper.writeValueAsString(responseDTO);

        mockMvc.perform(post("/train/add")
                .contentType("application/json")
                .content(trainDTOJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseDTOJson));
    }

    @Test
    public void testDeleteTrain() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setRes("Deleted Successfully");

        when(service.deleteTrain("12345")).thenReturn(responseDTO);

        String responseDTOJson = objectMapper.writeValueAsString(responseDTO);

        mockMvc.perform(delete("/train/delete/12345"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseDTOJson));
    }
    

    @Test
    public void testIsTrainNumberAvailable() throws Exception {
        when(service.isTrainNumberAvailable("12345")).thenReturn(true);

        mockMvc.perform(get("/train/isExistTrainNumber/12345"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testIsTrainNameAvailable() throws Exception {
        when(service.isTrainNameAvailable("Express")).thenReturn(true);

        mockMvc.perform(get("/train/isExistTrainName/Express"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testFindTrainFare() throws Exception {
        when(service.getFare("12345", "AC")).thenReturn(1000);

        mockMvc.perform(get("/train/getFare/12345/AC"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000"));
    }

    @Test
    public void testIsSeatAvl() throws Exception {
        when(service.avlSeats("12345", "AC", 10, "2025-05-05")).thenReturn(true);

        mockMvc.perform(get("/train/isSeatAvl/12345/AC/10/2025-05-05"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetTrain() throws Exception {
        TrainModel trainModel = new TrainModel("Express", "12345", "Mumbai", "Delhi", "10:00", "18:00", 10, 5, 1000, 5, 500, 10.0,"Espress");

        when(service.getTrainWithTrainNumber("12345")).thenReturn(trainModel);

        String trainModelJson = objectMapper.writeValueAsString(trainModel);

        mockMvc.perform(get("/train/train/12345"))
                .andExpect(status().isOk())
                .andExpect(content().json(trainModelJson));
    }

    @Test
    public void testGetTrainAvailablity() throws Exception {
        TrainAvailablity trainAvailablity = new TrainAvailablity();
        trainAvailablity.setAcAvailabilty(50);
        trainAvailablity.setSlAvailabilty(100);

        when(service.getTrainAvailablity("12345", "2025-05-05")).thenReturn(trainAvailablity);

        String trainAvailablityJson = objectMapper.writeValueAsString(trainAvailablity);

        mockMvc.perform(get("/train/trainAvl/12345/2025-05-05"))
                .andExpect(status().isOk())
                .andExpect(content().json(trainAvailablityJson));
    }

    @Test
    public void testTrainNumberUpdate() throws Exception {
        when(service.updateTrainNumber("12345", "54321")).thenReturn(true);

        mockMvc.perform(put("/train/updateTrainNumber/12345/54321"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testTrainNameUpdate() throws Exception {
        when(service.updateTrainName("Express", "Superfast")).thenReturn(true);

        mockMvc.perform(put("/train/updateTrainName/Express/Superfast"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

//    @Test
//    public void testCancelTrainTicket() throws Exception {
//        when(service.cancelTrainTicket("12345", "AC", 10, "2025-05-05")).thenReturn(true);
//
//        mockMvc.perform(post("/train/cancelTkt/12345/AC/10/2025-05-05"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("true"));
//    }
//
//    @Test
//    public void testBookTrainTicket() throws Exception {
//        when(service.bookTrainTicket("12345", "AC", 10, "2025-05-05")).thenReturn(true);
//
//        mockMvc.perform(post("/train/bookTrain/12345/AC/10/2025-05-05"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("true"));
//    }

//    @Test
//    public void testViewTrain() throws Exception {
//        TrainModel trainModel = new TrainModel("Express", "12345", "Mumbai", "Delhi", "10:00", "18:00", 10, 5, 1000, 5, 500, 10.0);
//
//        when(service.viewTrain("Mumbai", "Delhi","2025-02-09")).thenReturn(Collections.singletonList(trainModel));
//
//        String trainModelJson = objectMapper.writeValueAsString(Collections.singletonList(trainModel));
//
//        mockMvc.perform(get("/train/viewTicket/Mumbai/Delhi"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(trainModelJson));
//    }
}
