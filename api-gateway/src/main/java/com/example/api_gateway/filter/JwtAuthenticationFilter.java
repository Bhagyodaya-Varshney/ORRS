package com.example.api_gateway.filter;
 
import java.nio.charset.StandardCharsets;
import java.security.Key;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.core.HttpHeaders;
import reactor.core.publisher.Mono;


@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config>{
		
	public JwtAuthenticationFilter() {
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			
			String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			
			if(authHeader == null || !authHeader.startsWith("Bearer ")) {
				return onError(exchange, "Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED);
			}
			
			String token = authHeader.substring(7);		
			String role = validateToken(token);
			
			if(role==null) {
				return onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
			}
			
			if(role==null || !role.equals(config.getRole())) {
				return onError(exchange, "Unauthorized access", HttpStatus.FORBIDDEN);
			}
			
			return chain.filter(exchange);
		};
	}
	
	private String validateToken(String token) {
		try {
			byte[] key = Decoders.BASE64.decode("413F4428472B62506ertyufh55368566D597B337123");
			Key signInkey = Keys.hmacShaKeyFor(key);
			
			return Jwts.parserBuilder()
					.setSigningKey(signInkey).build().parseClaimsJws(token).getBody().get("role",String.class);
			
		}catch(Exception e) {
			return null;
		}
	}
	
	private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status){
		exchange.getResponse().setStatusCode(status);
		DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
		byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
		return exchange.getResponse().writeWith(Mono.just(bufferFactory.wrap(bytes)));	
	}
	
	public static class Config{
		private String role;
		public Config(String role) {
			this.role=role;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role=role;
		}
	}
}