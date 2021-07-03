package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private BigDecimal amount;
    //relates to UserIDs, not accountIDs
    private Long accountFrom;
    private Long accountId;

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



}
