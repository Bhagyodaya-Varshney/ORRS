package com.example.user_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.example.user_service.dto.LoginDTO;
import com.example.user_service.dto.RegisterResponse;
import com.example.user_service.dto.TokenResponse;
import com.example.user_service.dto.UserDTO;
import com.example.user_service.models.User;
import com.example.user_service.repositories.UserRepository;
import com.example.user_service.util.Jwt;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private Jwt jwtUtil;
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(BCryptPasswordEncoder.class);
        jwtUtil = mock(Jwt.class);
        authenticationManager = mock(AuthenticationManager.class);
        userService = new UserService();
        userService.repo = userRepository;
        userService.passEncoder = passwordEncoder;
        userService.util = jwtUtil;
        userService.authManager = authenticationManager;
    }

    @Test
    public void testRegisterUser_Success() {
        UserDTO userDTO = new UserDTO("test@example.com", "Test", "User", "password");
        User user = new User("test@example.com", "Test", "User", "encodedPassword");
        
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        RegisterResponse response = userService.registerUser(userDTO);

        assertEquals("User Registered Successfully!!", response.getMsg());
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() {
        UserDTO userDTO = new UserDTO("test@example.com", "Test", "User", "password");
        User user = new User("test@example.com", "Test", "User", "encodedPassword");
        
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));

        RegisterResponse response = userService.registerUser(userDTO);

        assertEquals("User Already Exists With Given Email", response.getMsg());
    }

    @Test
    public void testLoginUser_Success() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("password");
        
        User user = new User("test@example.com", "Test", "User", "encodedPassword");
        
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()))).thenReturn(null);
        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(user.getEmail(), user.getRole())).thenReturn("token");
        when(jwtUtil.generateRefreshToken(user.getEmail(), user.getRole())).thenReturn("refreshToken");

        TokenResponse response = userService.loginUser(loginDTO);

        assertEquals("token", response.getToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    public void testLoginUser_InvalidCredentials() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("wrongPassword");
        
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()))).thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        assertThrows(ResponseStatusException.class, () -> {
            userService.loginUser(loginDTO);
        });
    }
}
