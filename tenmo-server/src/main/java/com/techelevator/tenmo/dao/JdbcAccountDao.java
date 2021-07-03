package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;

    }
    @Override
    public Account getAccount(long id){
        String sql = "select * from accounts where user_id=?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

        Account account = new Account(results.getLong("account_id"), results.getLong("user_id"), results.getBigDecimal("balance"));
        return account;
    }



    @Override
    public void updateAccount(long userId, BigDecimal amount){
        String sql = "update accounts "+
                     "set balance = balance + ? "+
                     "where user_id = ?;";
        jdbcTemplate.update(sql, amount,userId);


    }

}
