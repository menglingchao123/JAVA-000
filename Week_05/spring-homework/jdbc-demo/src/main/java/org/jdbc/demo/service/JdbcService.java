package org.jdbc.demo.service;

import org.jdbc.demo.pojo.User;

import java.util.List;

public interface JdbcService {

    List<User> select(User user);

    void insert(User users);

    void update(User users);

    void delete(User user);
}
