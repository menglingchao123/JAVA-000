package org.jdbc.demo.service;

import org.jdbc.demo.pojo.User;

import java.util.List;

public interface BatchJdbcService {

    List<User> select(User user);

    void insert(List<User> users);

    void update(List<User> users);

    void delete(List<Integer> ids);
}
