package com.example.user_service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.user_service.dto.LoginDTO;
import com.example.user_service.dto.RegisterResponse;
import com.example.user_service.dto.TokenResponse;
import com.example.user_service.dto.UserDTO;
import com.example.user_service.exceptions.AuthenticationFailedException;
import com.example.user_service.exceptions.InvalidRoleException;
import com.example.user_service.exceptions.UserAlreadyExistsException;
import com.example.user_service.models.User;
import com.example.user_service.repositories.UserRepository;
import com.example.user_service.serviceInterface.UserServiceInterface;
import com.example.user_service.util.Jwt;

@Service
public class UserService implements UserServiceInterface{
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	BCryptPasswordEncoder passEncoder;
	
	@Autowired
	Jwt util;
	
	@Autowired
	AuthenticationManager authManager;
	
	public RegisterResponse registerUser(UserDTO userdto) {
		RegisterResponse res = new RegisterResponse();
		Optional<User> userExists = repo.findByEmail(userdto.getEmail());
		
		if(!userExists.isPresent()) {
			User user = new User(userdto.getFirstname(),userdto.getLastname(),userdto.getEmail(),passEncoder.encode(userdto.getPassword()));
			repo.save(user);
			
			res.setUsername(userdto.getEmail());
			res.setMsg("User Registered Successfully!!");
		}
		else {
			throw new UserAlreadyExistsException("User Already Exists With Given Email");
		}
		return res;
	}
	
	public TokenResponse loginUser(LoginDTO loginDTO) {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));
			Optional<User> userExists = repo.findByEmail(loginDTO.getEmail());
			String token = util.generateToken(userExists.get().getEmail(), userExists.get().getRole());
			String refreshToken = util.generateRefreshToken(userExists.get().getEmail(), userExists.get().getRole());
			
			TokenResponse res = new TokenResponse();
			res.setToken(token);
			res.setRefreshToken(refreshToken);
			
			return res;
		}
		catch(Exception e){
			throw new AuthenticationFailedException("Invalid email or password");
		}
	}
	
	public boolean updateCoins(String email, int coins) throws Exception {
		User user = repo.findByEmail(email).orElseThrow(()-> new Exception("Please Try Again Later"));
		user.setOrrsCoins(user.getOrrsCoins()+coins);
		repo.save(user);
		return true;
	}
	
	public int getCoins(String email) throws Exception{
		User user = repo.findByEmail(email).orElseThrow(()-> new Exception("Please Try Again Later"));
		return user.getOrrsCoins();
	}
	
}
