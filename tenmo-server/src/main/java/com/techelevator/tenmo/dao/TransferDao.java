package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<Transfer> getAll(int userAccount);

    boolean create(BigDecimal amount, int userIDTo, int userIDFrom);


}
