package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {
    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public AccountService(String url){this.baseUrl = url;}

    public BigDecimal getBalance(String token){
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(header);
        BigDecimal balance = BigDecimal.ZERO;
        try{
            ResponseEntity<BigDecimal> response = restTemplate.exchange(baseUrl+"/balance", HttpMethod.GET, entity, BigDecimal.class);
            balance = response.getBody();
        } catch (Exception e){
            System.out.println("ERROR occurred while getting balance" + e.getMessage());
        }
        return balance;
    }
}
