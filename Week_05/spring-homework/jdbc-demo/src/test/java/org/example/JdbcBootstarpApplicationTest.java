package org.example;

import static org.junit.Assert.assertTrue;

import org.jdbc.demo.JdbcBootstarpApplication;
import org.jdbc.demo.pojo.User;
import org.jdbc.demo.service.JdbcServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JdbcBootstarpApplication.class)
public class JdbcBootstarpApplicationTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue( true );
    }

    @Resource
    private JdbcServiceImpl jdbcServiceImpl;

    @Test
    public void select(){
        User user = User.builder().id(1).name("0").email("meng5351@126.com").build();
        List<User> users = jdbcServiceImpl.select(user);
        System.out.println(users);
    }

    @Test
    public void insert(){
        User user = User.builder().id(41).name("20").email("meng5351@163.com").build();
        jdbcServiceImpl.insert(user);
    }

    @Test
    public void update(){
        User user = User.builder().id(41).name("22").email("meng5351@163.com").build();
        jdbcServiceImpl.update(user);
    }

    @Test
    public void delete(){
        User user = User.builder().id(41).name("22").email("meng5351@163.com").build();
        jdbcServiceImpl.delete(user);
    }
}
