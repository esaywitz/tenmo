package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDaoForTest implements TransferDao{

    private JdbcTemplate jdbcTemplate;

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


    public JdbcTransferDaoForTest(DataSource dataSource){

        jdbcTemplate=new JdbcTemplate(dataSource);
    }





    public List<Transfer> getAll(Long accountID){
        List<Transfer> allTransfers = new ArrayList<>();

        if (accountID==0){
            allTransfers.add(new Transfer());
            return allTransfers;
        }

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
    public Long create(BigDecimal amount, Long userIDTo, Long userIDFrom){
        Long transferID = null;

        String sql = "INSERT INTO transfers (transfer_id, amount, account_to, account_from, transfer_type_id, transfer_status_id) " +
                "VALUES (?,?,?,?,?,?) RETURNING transfer_id; ";

        try {
            int transferId = jdbcTemplate.queryForObject(sql, Integer.class, 1004,amount, userIDTo, userIDFrom, 2, 2);
            transferID = (long) transferId;
        }
        catch (DataAccessException e){
            System.out.println(e.getCause() + ": " + e.getLocalizedMessage());
        }
        return transferID;
    }

    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setId(results.getLong("transfer_id"));
        //setting all transfer objects to default values of 3 for status (rejected)
        //and 2 for type (send). may need to be adjusted later.
        transfer.setAccountFrom(results.getInt("account_from"));
        transfer.setAccountTo(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));
        return transfer;
    }

}
