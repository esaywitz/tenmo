package com.techelevator.tenmo.model;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Account {


    private long AccountId;
    private long user_id;
    @Positive
    private BigDecimal balance;

    public Account(){

    }
    public Account(Long id, long user_id, BigDecimal balance){
        this.AccountId = id;
        this.user_id = user_id;
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getId() {
        return AccountId;
    }

    public void setId(long id) {
        this.AccountId = id;
    }
}
