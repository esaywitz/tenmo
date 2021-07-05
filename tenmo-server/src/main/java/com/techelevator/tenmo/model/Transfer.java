package com.techelevator.tenmo.model;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class Transfer {
    private Long transferID;

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
    private String status;
    private String type;

    public Transfer(){

    }


    public Transfer(BigDecimal amount, int accountTo, int accountFrom){
        this.amount = amount;
        this.accountTo = accountTo;
        this.accountFrom = accountFrom;
    }
    public Transfer(Long transferID,BigDecimal amount, int accountTo, int accountFrom){
        this.amount = amount;
        this.accountTo = accountTo;
        this.accountFrom = accountFrom;
        this.transferID=transferID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return transferID;
    }

    public void setId(Long transferID) {
        this.transferID = transferID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return "The transfer of" + amount + " from " + accountFrom + " to " + accountTo +
                " is " + status;
    }
}
