package com.example.admin_service.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.admin_service.dto.ResponseDTO;
import com.example.admin_service.serviceInterface.ServiceInterface;

public class ControllerTest {

    private MockMvc mockMvc;
    private ServiceInterface service;

    @BeforeEach
    public void setUp() {
        service = mock(ServiceInterface.class);
        Controller controller = new Controller();
        controller.service = service;
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGreet() throws Exception {
        mockMvc.perform(get("/admin/greet"))
                .andExpect(status().isOk())
                .andExpect(content().string("Admin Dashboard"));
    }

    @Test
    public void testUpdateTrainNumber_Success() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setRes("Train Deatils Updated");

        when(service.updateTrainNumber("12345", "54321")).thenReturn(responseDTO);

        mockMvc.perform(put("/admin/updateTrainNumber/12345/54321"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"res\":\"Train Deatils Updated\"}"));
    }

    @Test
    public void testUpdateTrainNumber_TrainDoesNotExist() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setRes("Train Does Not Exists");

        when(service.updateTrainNumber("12345", "54321")).thenReturn(responseDTO);

        mockMvc.perform(put("/admin/updateTrainNumber/12345/54321"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"res\":\"Train Does Not Exists\"}"));
    }

    @Test
    public void testUpdateTrainName_Success() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setRes("Train Deatils Updated");

        when(service.updateTrainName("Express", "Superfast")).thenReturn(responseDTO);

        mockMvc.perform(put("/admin/updateTrainName/Express/Superfast"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"res\":\"Train Deatils Updated\"}"));
    }

    @Test
    public void testUpdateTrainName_TrainDoesNotExist() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setRes("Train Does Not Exists");

        when(service.updateTrainName("Express", "Superfast")).thenReturn(responseDTO);

        mockMvc.perform(put("/admin/updateTrainName/Express/Superfast"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"res\":\"Train Does Not Exists\"}"));
    }
}
