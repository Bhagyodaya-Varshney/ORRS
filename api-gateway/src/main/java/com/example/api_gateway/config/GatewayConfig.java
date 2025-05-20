package com.example.api_gateway.config;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.gateway.route.RouteLocator;

import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import com.example.api_gateway.filter.JwtAuthenticationFilter;
 
 
@Configuration
public class GatewayConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public RouteLocator customRouteLoader(RouteLocatorBuilder builder) {

		return builder.routes()
				.route("user-service", r -> r
						.path("/user/**")
						.uri("http://localhost:8085/"))
				
				.route("booking-service", r -> r
						.path("/book/viewTrain/**")
						.uri("http://localhost:8083/"))
				
				.route("booking-service", r -> r
						.path("/book/viewTicket/**")
						.uri("http://localhost:8083/"))
				
				.route("railway-service", r -> r
						.path("/train/**")
						.filters(f ->f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config("ADMIN"))))
						.uri("http://localhost:8081/"))

				.route("admin-service", r -> r
						.path("/admin/**")
						.filters(f ->f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config("ADMIN"))))
						.uri("http://localhost:8082/"))

				.route("booking-service", r -> r
						.path("/book/**")
						.filters(f ->f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config("USER"))))
						.uri("http://localhost:8083/"))

				.build();	 

	}

}

 