package com.example.admin_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.admin_service.dto.ResponseDTO;
import com.example.admin_service.serviceInterface.TrainClient;

public class ServicesTest {

    @InjectMocks
    private Services services;

    @Mock
    private TrainClient trainClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateTrainNumber_Success() {
        when(trainClient.isTrainNumberAvailable("12345")).thenReturn(true);
        when(trainClient.trainNumberUpdate("12345", "54321")).thenReturn(true);

        ResponseDTO response = services.updateTrainNumber("12345", "54321");

        assertEquals("Train Details Updated", response.getRes());
    }

    @Test
    public void testUpdateTrainNumber_TrainDoesNotExist() {
        when(trainClient.isTrainNumberAvailable("12345")).thenReturn(false);

        ResponseDTO response = services.updateTrainNumber("12345", "54321");

        assertEquals("Train Does Not Exists", response.getRes());
    }

    @Test
    public void testUpdateTrainNumber_UnableToUpdate() {
        when(trainClient.isTrainNumberAvailable("12345")).thenReturn(true);
        when(trainClient.trainNumberUpdate("12345", "54321")).thenReturn(false);

        ResponseDTO response = services.updateTrainNumber("12345", "54321");

        assertEquals("Unable to Update the Train Details", response.getRes());
    }

    @Test
    public void testUpdateTrainName_Success() {
        when(trainClient.isTrainNameAvailable("Express")).thenReturn(true);
        when(trainClient.trainNameUpdate("Express", "Superfast")).thenReturn(true);

        ResponseDTO response = services.updateTrainName("Express", "Superfast");

        assertEquals("Train Details Updated", response.getRes());
    }

    @Test
    public void testUpdateTrainName_TrainDoesNotExist() {
        when(trainClient.isTrainNameAvailable("Express")).thenReturn(false);

        ResponseDTO response = services.updateTrainName("Express", "Superfast");

        assertEquals("Train Does Not Exists", response.getRes());
    }

    @Test
    public void testUpdateTrainName_UnableToUpdate() {
        when(trainClient.isTrainNameAvailable("Express")).thenReturn(true);
        when(trainClient.trainNameUpdate("Express", "Superfast")).thenReturn(false);

        ResponseDTO response = services.updateTrainName("Express", "Superfast");

        assertEquals("Unable to Update the Train Details", response.getRes());
    }
}
