package io.mlc.cache.cache.lettuce;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.Utf8StringCodec;
import io.lettuce.core.masterslave.MasterSlave;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;

public class LettuceSentinelDemo {

    public static void main(String[] args) {
        sentinelOperation();
    }

    //redis的sentinel配置方式,只配置主节点信息即可,通过主节点获取从节点信息,缓存起来,通过发布订阅方式动态更新缓存信息
    public static void sentinelOperation(){
        //构建连接信息，哨兵节点信息只需要配置一个即可,提供了哨兵拓扑发现机制自动获取其他哨兵节点信息
        RedisURI uri = RedisURI.builder().withSentinel("127.0.0.1", 26379).withSentinelMasterId("master").build();
        //创建redisclient
        RedisClient client = RedisClient.create(uri);
        //创建redis连接
        StatefulRedisMasterSlaveConnection<String, String> connect = MasterSlave.connect(client, new Utf8StringCodec(), uri);
        //设置读取策略
        connect.setReadFrom(ReadFrom.SLAVE_PREFERRED);
        //获取命令行工具
        RedisCommands<String, String> commands = connect.sync();
        //写入
        String result = commands.set("key1", "value1", SetArgs.Builder.nx().ex(5));
        System.out.println("插入结果==="+result);
        //读取操作
        String value = commands.get("key1");
        System.out.println("读取数据值====="+value);
        //释放连接
        connect.close();
        client.shutdown();
    }
}
