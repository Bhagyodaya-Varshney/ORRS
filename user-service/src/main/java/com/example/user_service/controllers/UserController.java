package com.example.user_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.dto.LoginDTO;
import com.example.user_service.dto.RegisterResponse;
import com.example.user_service.dto.TokenResponse;
import com.example.user_service.dto.UserDTO;
import com.example.user_service.serviceInterface.UserServiceInterface;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserServiceInterface service;
	
	@GetMapping("/")
	public String greet() {
		return "User Service";
	}
	
	@PostMapping("/register")
	public RegisterResponse registerUser(@RequestBody @Valid UserDTO userDto) {
		return service.registerUser(userDto);
	}
	
	@PostMapping("/login")
	public TokenResponse loginUser(@RequestBody @Valid LoginDTO loginDto) {
		return service.loginUser(loginDto);
	}
}
