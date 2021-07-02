package com.techelevator.tenmo.dao;

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
    public BigDecimal getBalance(long id){
        String sql = "select balance from accounts where user_id=?;";
        BigDecimal amout = jdbcTemplate.queryForObject(sql, BigDecimal.class, id);
        return amout;
    }



    @Override
    public void updateAccount(long userId, BigDecimal amount){
        String sql = "update accounts "+
                     "set balance = balance + ? "+
                     "where user_id = ?;";
        jdbcTemplate.update(sql, amount,userId);


    }

}