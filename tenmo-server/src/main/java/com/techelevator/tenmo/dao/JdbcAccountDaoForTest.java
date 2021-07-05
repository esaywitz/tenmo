package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class JdbcAccountDaoForTest implements AccountDao{

    private final JdbcTemplate jdbcTemplate;



    public JdbcAccountDaoForTest(DataSource dataSource){
        jdbcTemplate= new JdbcTemplate(dataSource);

    }




    @Override
    public Account getAccount(long id){
        String sql = "select account_id, user_id from accounts where user_id= ?;";
        String sql2 = "select balance from accounts where user_id=?;";
        BigDecimal balance = jdbcTemplate.queryForObject(sql2, BigDecimal.class,id);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        results.next();
        Account account = new Account(results.getLong("account_id"), results.getLong("user_id"), balance);
        return account;
    }



    @Override
    public void updateAccount(long userId, BigDecimal balance){
        String sql = "update accounts "+
                "set balance = balance + ? "+
                "where user_id = ?;";
        jdbcTemplate.update(sql, balance, userId);


    }
}
