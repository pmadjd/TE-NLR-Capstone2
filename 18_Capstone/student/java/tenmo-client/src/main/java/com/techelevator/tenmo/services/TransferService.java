package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransferService {
    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();


    public TransferService(String url){this.baseUrl = url;}

    public String getTransferHistory (String token){
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(header);
        String details = new String();
        try{
            ResponseEntity response = restTemplate.exchange(baseUrl+"/transfer/details", HttpMethod.GET, entity, String.class);
            details = (String) response.getBody();
        } catch (Exception e) {
            System.out.println("ERROR occurred while getting Transfer History");
        }
        return details;
    }

    public String getUserIds (String token){
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(header);
        String statement = new String();
        try{
            ResponseEntity response = restTemplate.exchange(baseUrl+"/transfer/users", HttpMethod.GET, entity, String.class);
            statement = (String) response.getBody();
        } catch (Exception e) {
            System.out.println("ERROR occurred while Getting Users");
        }
        return statement;
    }


    public String getSendBucks (String token, TransferDTO transferDTO) {  //, int userIdTo, int amount
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(token);
        HttpEntity<TransferDTO> entity = new HttpEntity<>(transferDTO, header); // make ? transfer DTO and pass in userIdTO and amount
        String statement = new String();
        try{
            ResponseEntity response = restTemplate.exchange(baseUrl+"/transfer/send", HttpMethod.POST, entity, String.class);
            statement = (String) response.getBody();
        } catch (Exception e) {
            System.out.println("ERROR occurred while Sending Bucks");
        }
        return statement;
    }
}
