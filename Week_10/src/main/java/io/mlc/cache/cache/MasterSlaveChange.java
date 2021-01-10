package io.mlc.cache.cache;

import redis.clients.jedis.Jedis;

public class MasterSlaveChange {

    public static void main(String[] args) {
        Jedis master = new Jedis("127.0.0.1", 6379);
        master.slaveofNoOne();//断开主从
        Jedis slave = new Jedis("127.0.0.1",6380);
        String result = slave.slaveof("127.0.0.1", 6379);
        //释放连接信息
        master.close();
        slave.close();
    }

    public void change(){
        Jedis master = new Jedis("127.0.0.1", 6379);
        //设置信息
        master.set("mlc","zkc");
        System.out.println(master.info());
        Jedis slave = new Jedis("127.0.0.1",6380);
        String mlc = slave.get("mlc");
        System.out.println("从节点获取数据");
    }
}
