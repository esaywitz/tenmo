package com.techelevator.tenmo.model;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class Transfer {
    private Long id;

    //@NotEmpty(message = "Transfer amount cannot be null.")
    //@Positive(message = "Transfer amount must be 1 or greater.")
    private BigDecimal amount;

    @Positive(message = "Transfer to account cannot be negative.")
    private long accountTo;

    @Positive(message = "Transfer from account cannot be negative.")
    private long accountFrom;

    //should we set status/type to the id or the description?
    //idea is to set the defaults and any changes can be handled in the
    //business logic
    private int status = 2;
    private int type = 2;

    public Transfer(){

    }


    public Transfer(BigDecimal amount, int accountTo, int accountFrom){
        this.amount = amount;
        this.accountTo = accountTo;
        this.accountFrom = accountFrom;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String toString() {
        return "The transfer of" + amount + " from " + accountFrom + " to " + accountTo +
                " is " + status;
    }
}