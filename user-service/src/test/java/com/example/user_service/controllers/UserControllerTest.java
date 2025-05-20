package com.example.user_service.controllers;

import com.example.user_service.dto.LoginDTO;
import com.example.user_service.dto.RegisterResponse;
import com.example.user_service.dto.TokenResponse;
import com.example.user_service.dto.UserDTO;
import com.example.user_service.serviceInterface.UserServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(UserControllerTest.MockedServiceConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    

    @Autowired
    private UserServiceInterface service;
    

    @TestConfiguration
    static class MockedServiceConfig {
    	
        @Bean
        public UserServiceInterface userServiceInterface() {
            return Mockito.mock(UserServiceInterface.class);
        }
    }

    @Test
    void testGreet() throws Exception {
        mockMvc.perform(get("/user/"))
                .andExpect(status().isOk())
                .andExpect(content().string("User Service"));
    }

    @Test
    void testRegisterUser() throws Exception {
        UserDTO userDTO = new UserDTO("Bhagyodaya", "Varshney", "mtrbhagyodayame@gmail.com", "Bhagy@1234");
        RegisterResponse res = new RegisterResponse();
        res.setUsername(userDTO.getEmail());
        res.setMsg("Registered Successfully");

        Mockito.when(service.registerUser(userDTO)).thenReturn(res);

        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Registered Successfully"));
    }

    @Test
    void testLoginUser() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("mtrbhagyodayame@gmail.com");
        loginDTO.setPassword("Bhagy@1234");

        TokenResponse res = new TokenResponse();
        res.setToken("Jwt.Token");
        res.setRefreshToken("Jwt.Refresh.Token");

        Mockito.when(service.loginUser(loginDTO)).thenReturn(res);

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("Jwt.Token"));
    }
}
