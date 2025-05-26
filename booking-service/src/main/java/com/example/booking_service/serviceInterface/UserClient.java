package com.example.booking_service.serviceInterface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {
	
	@PutMapping("/user/updateCoins/{email}/{coins}")
	public boolean updateCoins(@PathVariable String email, @PathVariable int coins);
	
	@GetMapping("/user/getCoins/{email}")
	public int getCoins(@PathVariable String email);

}
