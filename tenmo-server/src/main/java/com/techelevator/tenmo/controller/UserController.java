package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
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

    public UserController() {
        accountDao = new JdbcAccountDao();
        transferDao = new JdbcTransferDao();
    }

    @ResponseStatus(HttpStatus.OK)
    //shouldn't it be /users if that would return a list of all users?
    @RequestMapping(path = "/user/{id}/account" , method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable Long id){
        return accountDao.getBalance(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/user/{id}/account", method = RequestMethod.PUT)
    public void updateBalance(@PathVariable Long id, @RequestParam @Valid BigDecimal balance){
        accountDao.updateAccount(id, balance);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/user/{id}/account/{accountId}/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(@PathVariable Long id, @PathVariable Long accountId){
        return transferDao.getAll(id);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/user/{id}/account/{accountFrom}/transfers", method = RequestMethod.POST)
    public boolean create(@PathVariable Long id, @PathVariable Long accountFrom,
        @RequestParam Long accountTo, @RequestParam BigDecimal amount){
        return transferDao.create(amount, accountFrom, accountTo);
    }



}
