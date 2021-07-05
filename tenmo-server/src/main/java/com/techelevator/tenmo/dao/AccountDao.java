package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import javax.validation.Valid;
import java.math.BigDecimal;

public interface AccountDao {

    Account getAccount(long id);

    void updateAccount(long id, BigDecimal amount);
}
