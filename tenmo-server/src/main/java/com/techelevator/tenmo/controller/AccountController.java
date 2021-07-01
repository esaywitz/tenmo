package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AccountController {
    AccountDao accountDao;

    public AccountController() {
        accountDao = new JdbcAccountDao();
    }

    @RequestMapping(path = "/user/{id}/account" , method = RequestMethod.GET)
    public double getBalance(@PathVariable Long id){
        return accountDao.getBalance(id);
    }

    @RequestMapping(path = "/user/{id}/account", method = RequestMethod.PUT)
    public double updateBalance(@PathVariable Long id, @RequestParam @Valid double balance){
        return accountDao.updateAccount(id, balance);
    }



}
