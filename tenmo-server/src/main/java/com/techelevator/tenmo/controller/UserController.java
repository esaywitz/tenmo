package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {
    AccountDao accountDao;
    TransferDao transferDao;
    UserDao userDao;


    public UserController() {
        accountDao = new JdbcAccountDao();
        transferDao = new JdbcTransferDao();
    }



    @RequestMapping (path = "/users", method= RequestMethod.GET)
    public List<User> findAll(){
        return userDao.findAll();
    }

    @RequestMapping(path = "/users/{username}", method = RequestMethod.GET)
    public User findByUsername(@PathVariable @Valid String username){
        return userDao.findByUsername(username);
    }

    //same path and methods, won't compile
//    @RequestMapping(path = "/users/{username}", method = RequestMethod.GET)
//    public int findIdByUsername(@PathVariable @Valid String username){
//        return userDao.findIdByUsername(username);
//    }

    //isn't the /register endpoint the one that should be creating users?
    //shouldn't we be using a User object to create the record?
    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public boolean create(@RequestBody String username, @RequestBody String password){
        return userDao.create(username, password);
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/users/{id}/account" , method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable Long id){
        return accountDao.getBalance(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/users/{id}/account", method = RequestMethod.PUT)
    public void updateBalance(@PathVariable Long id, @RequestParam @Valid BigDecimal balance){
        accountDao.updateAccount(id, balance);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/users/{id}/account/{accountId}/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(@PathVariable Long id, @PathVariable Long accountId){
        return transferDao.getAll(id);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/users/{id}/account/{accountFrom}/transfers", method = RequestMethod.POST)
    public boolean create(@PathVariable Long id, @PathVariable Long accountFrom,
        @RequestParam Long accountTo, @RequestParam BigDecimal amount){
        return transferDao.create(amount, accountFrom, accountTo);
    }



}
