package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getBalance(Long id);

    void updateAccount(Long id, BigDecimal amount);
}
