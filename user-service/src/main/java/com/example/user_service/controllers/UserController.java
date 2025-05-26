package com.example.user_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@PutMapping("/updateCoins/{email}/{coins}")
	public boolean updateCoins(@PathVariable String email, @PathVariable int coins) throws Exception {
		return service.updateCoins(email,coins);
	}
	
	@GetMapping("/getCoins/{email}")
	public int getCoins(@PathVariable String email) throws Exception {
		return service.getCoins(email);
	}
}
