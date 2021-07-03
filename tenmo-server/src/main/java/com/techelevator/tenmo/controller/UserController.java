package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class UserController {
    TransferDao transferDao;
    AccountDao accountDao;
    UserDao userDao;

    public UserController(AccountDao accountDao, UserDao userDao, TransferDao transferDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transferDao = transferDao;

    }

    @RequestMapping(path = "/users/{id}/accounts" , method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable long id){
        return accountDao.getBalance(id);
    }

    @RequestMapping(path = "/users/{id}/accounts", method = RequestMethod.PUT)
    public void updateBalance(@PathVariable long id, @RequestParam @Valid BigDecimal balance){
        accountDao.updateAccount(id, balance);
    }
    @RequestMapping (path = "/users", method= RequestMethod.GET)
    public List<User> findAll(){
        return userDao.findAll();
    }

    @RequestMapping(path = "/users/{username}", method = RequestMethod.GET)
    public User findByUsername(@PathVariable @Valid String username){
       return userDao.findByUsername(username);
    }
    

    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public boolean create(@RequestParam @Valid String username, @RequestParam @Valid String password){
        return userDao.create(username, password);
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(@RequestParam @Valid Long accountId){
        return transferDao.getAll(accountId);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public boolean create(@RequestBody @Valid Transfer transfer){
        return transferDao.create(transfer.getAmount(), transfer.getAccountTo(), transfer.getAccountFrom());
    } 



}
