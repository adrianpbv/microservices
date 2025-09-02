package com.bankdemo.accounts.service.client;

import com.bankdemo.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards") // TODO step 7 cards is the name of the instance on the Eureka server
public interface CardsFeignClient {
     @GetMapping(value = "api/fetch", consumes = "application/json")
     ResponseEntity<CardsDto> fetchCardDetails(@RequestParam String mobileNumber);
}
