package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Transfer> getAll(int userID){
        List<Transfer> allTransfers = new ArrayList<>();

        //join transfer, account and user tables
        String sql = "SELECT user_id, transfer_id, account_from, account_to, amount \n" +
                "FROM transfers\n" +
                "JOIN accounts ON (account_from = account_id)\n" +
                "JOIN users USING (user_id)\n" +
                "WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userID);
        while(results.next()){
            allTransfers.add(mapRowToTransfer(results));
        }
        return allTransfers;
    }

    public boolean create(BigDecimal amount, int userIDTo, int userIDFrom){
        boolean created = false;
        //this is probably insufficient, needs an additional statement to map userID

        String sql = "INSERT INTO transfers (amount, accountTo, accountFrom) VALUES (?, ?, ?) " +
                "RETURNING transfer_id";
        try {
           int newTransferID = jdbcTemplate.queryForObject(sql, Integer.class, userIDTo, userIDFrom);
           created = true;
        }
        catch (DataAccessException e){
            created = false;
        }
        return created;
    }

    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setId(results.getLong("transfer_id"));
        //setting all transfer objects to default values of 3 for status (rejected)
        //and 2 for type (send). may need to be adjusted later.
        transfer.setStatus(2);
        transfer.setType(2);
        transfer.setAccountFrom(results.getInt("account_from"));
        transfer.setAccountTo(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));
        return transfer;
    }

}
