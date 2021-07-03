package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private Long transferID;
    private BigDecimal amount;

    private Long accountFrom;
    private Long accountTo;
    private Long accountId;

    public Transfer(BigDecimal amount, Long accountFrom, Long accountTo){
        this.amount = amount;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
    }

    public Transfer(){}

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Long accountTo) {
        this.accountTo = accountTo;
    }

    public Long getTransferID() {
        return transferID;
    }

    public void setTransferID(Long transferID) {
        this.transferID = transferID;
    }
}
