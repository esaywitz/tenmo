package com.techelevator;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.techelevator.tenmo.dao.JdbcUserDaoForTest;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JdbcUserDaoTest extends TenmoDaoTest{

    private static final User USER_1 = new User((long)1,"user1", "test1Password","");
    private static final User USER_2 = new User((long)2,"user2", "test2Password","");
    private static final User USER_3 = new User((long)3,"user3", "test3Password","");



    private JdbcUserDaoForTest sut;

    @Before
    public void setup(){
        sut = new JdbcUserDaoForTest(dataSource);
    }

    @Test
    public void findByUsername_returns_correct_user_for_username(){
        User user = sut.findByUsername("user1");
        assertUsersMatch(USER_1, user);

    }
    @Test
    public void findAll_returns_all_users(){
        List<User> actual = sut.findAll();
        assertUsersMatch(USER_1, actual.get(0));
        assertUsersMatch(USER_2, actual.get(1));
        assertUsersMatch(USER_3, actual.get(2));


    }

    @Test
    public void create_returns_true_if_created(){
        boolean actual = sut.create("test1", "test1");
        Assert.assertTrue(actual);



    }

    private void assertUsersMatch(User expected, User actual){
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
    }
}
