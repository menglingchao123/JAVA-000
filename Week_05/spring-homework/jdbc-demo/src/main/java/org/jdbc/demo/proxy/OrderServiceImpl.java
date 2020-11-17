package org.jdbc.demo.proxy;

import org.assertj.core.util.Lists;

import java.util.List;

public class OrderServiceImpl implements OrderService{
    @Override
    public List<String> get() {
        return Lists.newArrayList("1","2");
    }
}
