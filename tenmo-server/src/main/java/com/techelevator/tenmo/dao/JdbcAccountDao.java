package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;
    @Override
    public BigDecimal getBalance(Long id){
        String sql = "select balance from accounts where user_id=?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        BigDecimal amount = result.getBigDecimal("balance");
        return amount;
    }



    @Override
    public void updateAccount(Long userId, BigDecimal amount){
        String sql = "update accounts"+
                     "set balance = balance + ? "+
                     "where user_id = ?;";
        jdbcTemplate.update(sql, amount,userId );


    }

}
