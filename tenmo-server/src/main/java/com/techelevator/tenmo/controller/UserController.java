package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class UserController {
    AccountDao accountDao;
    UserDao userDao;

    public UserController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;

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




}