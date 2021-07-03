package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private String url = "http:localhost:8080/";
    public static String AUTH_TOKEN = "";
    private RestTemplate restTemplate = new RestTemplate();


    private AccountService(String url){
        this.url = url;
    }

    public Transfer[] getAll(){
        Transfer[] transfers = null;
        try{
            transfers = restTemplate.exchange(url + "/user/{id}/account", HttpMethod.GET, makeAuthEntity(),
                   Transfer[].class).getBody();
        }
        catch (RestClientResponseException e){
            System.out.println(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
        }
        return transfers;
    }




    private HttpEntity makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

}
