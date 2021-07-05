package com.techelevator.tenmo.services;

import com.techelevator.tenmo.App;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private final String BASE_URL = "http://localhost:8080";
    public String AUTH_TOKEN = "";
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

    public Transfer getTransfer(Long accountId, Long id){
        Transfer transfer = null;
        try{
            transfer = restTemplate.exchange(BASE_URL + "/transfer?accountId=" + accountId + "&id=" + id, HttpMethod.GET, makeAuthEntity(),
                    Transfer.class).getBody();
        }
        catch (RestClientResponseException e){
            System.out.println(e.getRawStatusCode() + ": " + e.getResponseBodyAsString());
        }
        return transfer;
    }

    public Account getAccount(Long userId){
        Account account = null;
        try {

            account = restTemplate.exchange(BASE_URL + "/users/"+ userId+"/accounts" , HttpMethod.GET,
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

    public User[] getAllUsers(){
       User[] users = null;
        try{
            users = restTemplate.exchange(BASE_URL + "/users", HttpMethod.GET, makeAuthEntity(),
                    User[].class).getBody();
        } catch (RestClientResponseException e){
            System.out.println(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
        }
        return users;
    }

    public Long createTransfer(Transfer transfer){
        Long transferID = null;
        try{
           transferID = restTemplate.postForObject(BASE_URL + "transfers", makeTransferEntity(transfer), Long.class);
        }
        catch(RestClientResponseException e){
            System.out.println(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
        }
        return transferID;
    }

    public void updateBalance(Long userId, BigDecimal balance){
        try{
            restTemplate.exchange(BASE_URL + "/users/" + userId + "/accounts?balance=" + balance, HttpMethod.PUT, makeBalanceEntity(balance), BigDecimal.class);
        }
        catch (RestClientResponseException e){
            System.out.println(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
        }
    }

    //this is used for any POST requests
    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        AUTH_TOKEN = App.getCurrentUser().getToken();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }

    //used for PUT requests to update balance
    private HttpEntity<BigDecimal> makeBalanceEntity(BigDecimal amount){
        HttpHeaders headers = new HttpHeaders();
        AUTH_TOKEN = App.getCurrentUser().getToken();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity<BigDecimal> entity = new HttpEntity<>(amount, headers);
        return entity;
    }


    private HttpEntity makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        AUTH_TOKEN = App.getCurrentUser().getToken();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

}
