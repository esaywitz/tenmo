package com.techelevator;

import com.techelevator.tenmo.dao.JdbcAccountDaoForTest;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class JdbcAccountDaoTest extends TenmoDaoTest{


    private static final Account ACCOUNT_1 = new Account(101,1, BigDecimal.valueOf(1000));
    private static final Account ACCOUNT_2 = new Account(102,2, BigDecimal.valueOf(950));
    private static final Account ACCOUNT_2Modified = new Account(102,2, BigDecimal.valueOf(900));



    private JdbcAccountDaoForTest sut;

    @Before
    public void setup(){
        sut = new JdbcAccountDaoForTest(dataSource);
    }

    @Test
    public void getAccount_returns_account1_for_1(){
        Account account = sut.getAccount(1);
        assertAccountsMatch(ACCOUNT_1, account);


    }

    @Test
    public void updateAccount_Account2_will_have_a_balance_of_900(){
        sut.updateAccount(2,BigDecimal.valueOf(-50));
        Account account = sut.getAccount(2);

        assertAccountsMatch(ACCOUNT_2Modified, account);
    }

    public void assertAccountsMatch(Account expected, Account actual){
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(expected.getUser_id(), expected.getUser_id());
        Assert.assertEquals(expected.getBalance(), expected.getBalance());
    }
}
