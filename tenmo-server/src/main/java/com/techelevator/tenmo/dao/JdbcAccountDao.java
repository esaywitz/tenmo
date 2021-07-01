package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;
    @Override
    public double getBalance(Long id){
        String sql = "select balance from accounts where user_id=?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        double amount = result.getDouble("balance");
        return amount;
    }



    @Override
    public double updateAccount(Long id, double amount){
        String sql = "update accounts"+
                     "set balance = balance + ? "+
                     "where user_id = ? returning balance;";
        return jdbcTemplate.update(sql, amount,id, double.class );


    }

}
