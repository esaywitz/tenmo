package com.techelevator.tenmo.dao;

public interface AccountDao {

    double getBalance(Long id);



    double updateAccount(Long id, double amount);
}
