package org.jdbc.demo.service;

import org.jdbc.demo.annonation.Transaction;
import org.jdbc.demo.config.JdbcConfiguration;
import org.jdbc.demo.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 事务通过AOP代理方式实现,通过jdbc的批处理操作实现一次处理多条sql
 *
 * @author lingchaomeng
 * @Date 2020/11/17
 */
@Service
public class ProxyBatchJdbcServiceImpl implements BatchJdbcService {

    @Resource
    private JdbcConfiguration jdbcConfiguration;

    //查询不能进行批处理
    public List<User> select(User user) {
        String sql = "select * from user where id = ? and name = ? and email = ?";
        try (
                //获取连接
                Connection connection = jdbcConfiguration.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            //设置请求参数
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            //获取返回值
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            //解析返回值
            while (resultSet.next()) {
                users.add(User.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .email(resultSet.getString("email"))
                        .build());
            }
            return users;
        } catch (SQLException t) {
            t.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Transaction
    public void insert(List<User> users) {
        //批处理表示一次执行多条sql
        String sql = "INSERT INTO user (id,`name`,email) VALUES (?,?,?)";
        try (
                //获取连接
                Connection connection = jdbcConfiguration.getConnection();
                PreparedStatement p = connection.prepareStatement(sql);
        ) {
            for (User user : users) {
                p.setInt(1, user.getId());
                p.setString(2, user.getName());
                p.setString(3, user.getEmail());
                p.addBatch();
            }
            p.execute();
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }

    @Transaction
    public void update(List<User> users) {
        String sql = "update user set name = ?,email = ? where id = ?";
        try (
                //获取连接
                Connection connection = jdbcConfiguration.getConnection();
                PreparedStatement p = connection.prepareStatement(sql);
        ) {
            for (User user : users) {
                p.setString(1, user.getName());
                p.setString(2, user.getEmail());
                p.setInt(3, user.getId());
                p.addBatch();
            }
            p.execute();
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }

    @Transaction
    public void delete(List<Integer> ids) {
        String sql = "delete from user where id = ?";
        try (
                Connection connection = jdbcConfiguration.getConnection();
                PreparedStatement p = connection.prepareStatement(sql);
        ) {
            for (Integer id : ids) {
                p.setInt(1, id);
                p.addBatch();
            }
            p.execute();
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }
}
