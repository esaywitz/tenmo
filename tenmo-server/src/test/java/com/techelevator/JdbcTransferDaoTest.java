package com.techelevator;

import com.techelevator.tenmo.dao.JdbcTransferDaoForTest;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDaoTest extends TenmoDaoTest{

    private static final Transfer TRANSFER_1 = new Transfer((long)1001,BigDecimal.valueOf(50), 101, 102);
    private static final Transfer TRANSFER_2 = new Transfer((long)1002,BigDecimal.valueOf(100),101, 103);
    private static final Transfer TRANSFER_3 = new Transfer((long)1003,BigDecimal.valueOf(150),103, 101);
    private static final Transfer TRANSFER_4 = new Transfer((long)1004,BigDecimal.valueOf(150),103, 101);
    private JdbcTransferDaoForTest sut;

    @Before
    public void setup(){
        sut = new JdbcTransferDaoForTest(dataSource);
    }

    @Test
    public void getAll_returns_all_transfers(){
        List<Transfer> actual1 = sut.getAll((long)101);
        Assert.assertEquals(3, actual1.size());

        List<Transfer> actual2 = sut.getAll((long)103);
        Assert.assertEquals(2, actual2.size());


    }

    @Test
    public void create_returns_1004_for_a_new_transfer(){
        Long actual = sut.create(TRANSFER_4.getAmount(), TRANSFER_4.getAccountTo(), TRANSFER_4.getAccountFrom());
        Long expected = (long)1004;
        Assert.assertEquals(expected, actual);
    }

}
