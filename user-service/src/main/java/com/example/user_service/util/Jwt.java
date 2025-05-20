package com.example.user_service.util;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Jwt {

	public String generateToken(String username, String role) {
		return Jwts.builder().setSubject(username).claim("role", role).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 1000*60*24)).signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
	}
	
	public String generateRefreshToken(String username, String role) {
	    return Jwts.builder()
	               .setSubject(username)
	               .claim("role", role)
	               .setIssuedAt(new Date(System.currentTimeMillis()))
	               .setExpiration(new Date(System.currentTimeMillis() + 604800000)) // 7 days
	               .signWith(getSignKey(), SignatureAlgorithm.HS256)
	               .compact();
	}

	
	private Key getSignKey() {
		byte[] key = Decoders.BASE64.decode("413F4428472B62506ertyufh55368566D597B337123");
        return Keys.hmacShaKeyFor(key);
	}
	
	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	public String extractRole(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody().get("role",String.class);
	}
	
	public boolean isTokenValid(String token, String username) {
		final String name = extractUsername(token);
		return name.equals(username);
	}
	
	public boolean isTokenExpired(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
	}
}
