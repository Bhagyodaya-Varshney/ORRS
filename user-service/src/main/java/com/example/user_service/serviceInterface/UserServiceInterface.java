package com.example.user_service.serviceInterface;

import com.example.user_service.dto.LoginDTO;
import com.example.user_service.dto.RegisterResponse;
import com.example.user_service.dto.TokenResponse;
import com.example.user_service.dto.UserDTO;

public interface UserServiceInterface {
	RegisterResponse registerUser(UserDTO userdto);
	TokenResponse loginUser(LoginDTO loginDTO);
}
