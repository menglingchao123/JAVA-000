package org.jdbc.demo.service;

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
 * jdbc作业1:通过jdbc实现增删改查操作
 * @author lingchaomeng
 * @Date 2020/11/17
 */
@Service
public class JdbcServiceImpl implements JdbcService {

    @Resource
    private JdbcConfiguration jdbcConfiguration;

    public List<User> select(User user) {
        String sql = "select * from user where id = ? and name = ? and email = ?";
        //获取连接
        Connection connection = jdbcConfiguration.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            //创建连接通道
            preparedStatement = connection.prepareStatement(sql);
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
        } finally {
            jdbcConfiguration.closeStatement(preparedStatement);
            jdbcConfiguration.closeConnection(connection);
        }
        return Collections.emptyList();
    }

    public void insert(User user) {
        String sql = "INSERT INTO user (id,`name`,email) VALUES (?,?,?)";
        //获取连接
        Connection connection = jdbcConfiguration.getConnection();
        PreparedStatement p = null;
        try {
            connection.setAutoCommit(false);
            p = connection.prepareStatement(sql);
            p.setInt(1, user.getId());
            p.setString(2, user.getName());
            p.setString(3, user.getEmail());
            p.execute();
            connection.commit();
        } catch (SQLException t) {
            jdbcConfiguration.rollback(connection);
            t.printStackTrace();
        } finally {
            jdbcConfiguration.closeStatement(p);
            jdbcConfiguration.closeConnection(connection);
        }
    }

    public void update(User user) {
        String sql = "update user set name = ?,email = ? where id = ?";
        //获取连接
        Connection connection = jdbcConfiguration.getConnection();
        PreparedStatement p = null;
        try {
            connection.setAutoCommit(false);
            p = connection.prepareStatement(sql);
            p.setString(1, user.getName());
            p.setString(2, user.getEmail());
            p.setInt(3, user.getId());
            p.execute();
            connection.commit();
        } catch (SQLException t) {
            jdbcConfiguration.rollback(connection);
            t.printStackTrace();
        } finally {
            jdbcConfiguration.closeStatement(p);
            jdbcConfiguration.closeConnection(connection);
        }
    }

    public void delete(User user) {
        String sql = "delete from user where id = ? and name = ? and email = ?";
        Connection connection = jdbcConfiguration.getConnection();
        PreparedStatement p = null;
        try {
            connection.setAutoCommit(false);
            p = connection.prepareStatement(sql);
            p.setInt(1, user.getId());
            p.setString(2, user.getName());
            p.setString(3, user.getEmail());
            p.execute();
            connection.commit();
        } catch (SQLException t) {
            jdbcConfiguration.rollback(connection);
            t.printStackTrace();
        } finally {
            jdbcConfiguration.closeStatement(p);
            jdbcConfiguration.closeConnection(connection);
        }
    }
}
