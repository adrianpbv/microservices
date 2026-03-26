package com.bankdemo.accounts.service.client;

import com.bankdemo.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

// TODO 5
@FeignClient(name = "loans", url = "http://loans:8090",fallback = LoansFallback.class) // loans is the name of the instance on the Eureka server
public interface LoansFeignClient {
    @GetMapping(value = "api/fetch", consumes = "application/json")
    ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("eazybank-correlation-id") String correlationId,
                                              @RequestParam String mobileNumber);
}
