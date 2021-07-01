package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class UserController {
    AccountDao accountDao;

    public UserController() {
        accountDao = new JdbcAccountDao();
    }

    @RequestMapping(path = "/user/{id}/account" , method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable Long id){
        return accountDao.getBalance(id);
    }

    @RequestMapping(path = "/user/{id}/account", method = RequestMethod.PUT)
    public void updateBalance(@PathVariable Long id, @RequestParam @Valid BigDecimal balance){
        accountDao.updateAccount(id, balance);
    }



}
