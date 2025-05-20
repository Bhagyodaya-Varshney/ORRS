package com.example.user_service.dto;

import lombok.Data;

@Data
public class TokenResponse {

	private String token;
	private String refreshToken;
}
