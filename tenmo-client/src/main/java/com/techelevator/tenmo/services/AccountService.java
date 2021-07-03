package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private final String BASE_URL = "http://localhost:8080/";
    public static String AUTH_TOKEN = "";
    private final RestTemplate restTemplate;


    public AccountService(){
        restTemplate = new RestTemplate();
    }

    public Transfer[] getAll(Long accountId){
        Transfer[] transfers = null;
        try{
            transfers = restTemplate.exchange(BASE_URL + "/transfers?accountId=" + accountId , HttpMethod.GET, makeAuthEntity(),
                   Transfer[].class).getBody();
        }
        catch (RestClientResponseException e){
            System.out.println(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
        }
        return transfers;
    }
    public Account getAccount(Long userId){
        Account account = null;
        try {

            account = restTemplate.exchange(BASE_URL + "/users/"+ userId+"/accounts", HttpMethod.GET,
                    makeAuthEntity(), Account.class).getBody();
        } catch (RestClientResponseException e){
            System.out.println(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
        }
        return account;
    }
    public User getUser(String username){
        User user = null;
        try {
            user = restTemplate.getForObject(BASE_URL + "/users/" + username, User.class,
                    makeAuthEntity());
        } catch (RestClientResponseException e){
            System.out.println(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
        }

        return user;

    }



    private HttpEntity makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

}
