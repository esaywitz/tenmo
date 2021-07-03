package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate){

        this.jdbcTemplate=jdbcTemplate;
    }



    public List<Transfer> getAll(Long accountID){
        List<Transfer> allTransfers = new ArrayList<>();

        if (accountID==0){
            allTransfers.add(new Transfer());
            return allTransfers;
        }

        //join transfer, account and user tables
        String sql = "SELECT transfer_id, account_from, account_to, amount " +
                "FROM transfers " +
                "WHERE account_from = ? OR account_to=?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountID, accountID);
        while(results.next()){
            allTransfers.add(mapRowToTransfer(results));
        }
        return allTransfers;
    }


    @Override
    public boolean create(BigDecimal amount, Long userIDTo, Long userIDFrom){
        boolean created = false;
        //this is probably insufficient, needs an additional statement to map userID

        String sql = "INSERT INTO transfers (amount, account_to, account_from, transfer_type_id, transfer_status_id) " +
                "VALUES (?,?,?,?,?); ";

        try {

            jdbcTemplate.update(sql, amount, userIDTo, userIDFrom, 2, 2);

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