package com.techelevator.tenmo.model;

import javax.validation.constraints.Positive;

public class Account {


    private long id;
    private long user_id;
    @Positive
    private double balance;

    public Account(){

    }
    public Account(Long id, long user_id, double balance){
        this.id = id;
        this.user_id = user_id;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
