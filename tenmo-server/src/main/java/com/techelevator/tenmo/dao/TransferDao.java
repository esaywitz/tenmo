package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<Transfer> getAll(Long userId);

    Long create(BigDecimal amount, Long userIDTo, Long userIDFrom);

    Transfer getTransfer(Long id);
}
