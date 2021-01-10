package io.mlc.cache.cache.redission;

import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissionSentinel {

    public static void main(String[] args) {

        //创建配置
        Config config = new Config();
        config.useSentinelServers()
                .setMasterName("master")
                .addSentinelAddress("redis://127.0.0.1:26379", "redis://127.0.0.1:26380", "redis://127.0.0.1:26381");
        //创建redis客户端
        RedissonClient client = Redisson.create(config);

        //执行操作
        RList<Object> list = client.getList("zkx");
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        list.forEach(System.out::println);

        //释放连接
        client.shutdown();
    }
}
