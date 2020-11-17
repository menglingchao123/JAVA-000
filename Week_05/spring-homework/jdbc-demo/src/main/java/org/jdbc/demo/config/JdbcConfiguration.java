package org.jdbc.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Data
@Component
public class JdbcConfiguration {

    private static final ThreadLocal<Connection> owner = new ThreadLocal<>();

    @Resource
    private JdbcConnectionProperties jdbcConnectionProperties;

    @Resource
    private DataSource dataSource;

    //通过jdbc获取连接
    public Connection getJdbcConnection() {
        Connection connection = owner.get();
        try {
            if (connection != null)
                return connection;
            //加载驱动类
            Class.forName(jdbcConnectionProperties.getDriver());
            //获取数据库连接
            connection = DriverManager.getConnection(jdbcConnectionProperties.getUrl(), jdbcConnectionProperties.getUsername(), jdbcConnectionProperties.getPassword());
            owner.set(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //通过连接池获取连接
    public Connection getConnection() {
        Connection connection = owner.get();
        try {
            if (connection != null)
                return connection;
            connection = dataSource.getConnection();
            owner.set(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException t) {
            t.printStackTrace();
        }finally {
            owner.remove();
        }
    }

    public void closeStatement(Statement statement) {
        try {
            statement.close();
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }

    public void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
