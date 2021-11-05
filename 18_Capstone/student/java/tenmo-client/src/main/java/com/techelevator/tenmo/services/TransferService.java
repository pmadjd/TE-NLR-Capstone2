package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
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

    public List<String> getTransferHistory (String token){
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(header);
        List<String> details = new ArrayList();
        try{
            ResponseEntity<List> response = restTemplate.exchange(baseUrl+"/transfer", HttpMethod.GET, entity, List.class);
            details = response.getBody();
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


    public String getSendBucks (String token) {
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(header);
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
