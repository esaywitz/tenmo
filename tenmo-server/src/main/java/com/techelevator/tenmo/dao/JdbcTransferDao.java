package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.LongBinaryOperator;

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

        String sql = "SELECT transfer_id, account_from, account_to, amount, transfer_type_desc, transfer_status_desc " +
                "FROM transfers " +
                "JOIN transfer_statuses USING(transfer_status_id)\n" +
                "JOIN transfer_types USING(transfer_type_id)\n" +
                "WHERE account_from = ? OR account_to = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountID, accountID);
        while(results.next()){
            allTransfers.add(mapRowToTransfer(results));
        }
        return allTransfers;
    }


    @Override
    public Long create(BigDecimal amount, Long userIDTo, Long userIDFrom){
        Long transferID = null;

        String sql = "INSERT INTO transfers (amount, account_to, account_from, transfer_type_id, transfer_status_id) " +
                "VALUES (?,?,?,?,?) RETURNING transfer_id; ";

        try {
            int transferId = jdbcTemplate.queryForObject(sql, Integer.class, amount, userIDTo, userIDFrom, 2, 2);
            transferID = Long.valueOf(transferId);
        }
        catch (DataAccessException e){
            System.out.println(e.getCause() + ": " + e.getLocalizedMessage());
        }
        return transferID;
    }


    public Transfer getTransfer(Long id){
        Transfer transfer = null;
        String sql = "SELECT transfer_id, account_from, account_to, amount, transfer_status_desc, transfer_type_desc\n" +
                "FROM transfers\n" +
                "JOIN transfer_statuses USING(transfer_status_id)\n" +
                "JOIN transfer_types USING(transfer_type_id)\n" +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        while(results.next()){
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }


    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setId(results.getLong("transfer_id"));
        //setting all transfer objects to default values of 3 for status (rejected)
        //and 2 for type (send). may need to be adjusted later.
        transfer.setStatus(results.getString("transfer_status_desc"));
        transfer.setType(results.getString("transfer_type_desc"));
        transfer.setAccountFrom(results.getInt("account_from"));
        transfer.setAccountTo(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));
        return transfer;
    }

}
