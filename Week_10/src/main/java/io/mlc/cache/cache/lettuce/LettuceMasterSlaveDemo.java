package io.mlc.cache.cache.lettuce;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.Utf8StringCodec;
import io.lettuce.core.masterslave.MasterSlave;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;

import java.util.ArrayList;
import java.util.List;

public class LettuceMasterSlaveDemo {

    public static void main(String[] args) {
        masterSlave1();
        masterSlave2();
    }

    //创建主从连接 , 直接配置多个节点信息
    public static void masterSlave1(){
        List<RedisURI> uris = new ArrayList<>();
        RedisURI uri1 = RedisURI.builder().withHost("127.0.0.1").withPort(6379).build();
        RedisURI uri2 = RedisURI.builder().withHost("127.0.0.1").withPort(6380).build();
        RedisURI uri3 = RedisURI.builder().withHost("127.0.0.1").withPort(6381).build();
        uris.add(uri1);
        uris.add(uri2);
        uris.add(uri3);
        RedisClient redisClient = RedisClient.create();
        //通过masterSlave获取连接
        StatefulRedisMasterSlaveConnection<String, String> connect = MasterSlave.connect(redisClient, new Utf8StringCodec(), uris);
        //设置读取偏好
        connect.setReadFrom(ReadFrom.SLAVE_PREFERRED);
        //执行redis命令实例
        RedisCommands<String, String> commands = connect.sync();
        //执行命令操作
        SetArgs args = SetArgs.Builder.nx().ex(5);
        //写入
        String result = commands.set("key1", "value1", args);
        System.out.println("插入结果==="+result);
        //读取操作
        String value = commands.get("key1");
        System.out.println("读取数据值====="+value);
        //释放连接
        connect.close();
        redisClient.shutdown();
    }

    //1.只配置主节点通过主节点info信息获取从节点信息,然后放入静态缓存中不进行更新缓存
    public static void masterSlave2(){
        //构建redis连接信息
        RedisURI redisURI = RedisURI.builder().withHost("127.0.0.1").withPort(6379).build();
        //创建redis的client
        RedisClient redisClient = RedisClient.create(redisURI);
        //获取redis主从连接
        StatefulRedisMasterSlaveConnection<String, String> connect = MasterSlave.connect(redisClient, new Utf8StringCodec(), redisURI);
        //设置读偏好
        connect.setReadFrom(ReadFrom.SLAVE_PREFERRED);
        //获取命令行工具
        RedisCommands<String, String> commands = connect.sync();
        //执行操作命令
        String result = commands.set("zkx", "mlc", SetArgs.Builder.nx().ex(5));
        System.out.println("写操作执行结果===="+result);
        //读取操作
        String value = commands.get("zkx");
        System.out.println("读取结果===="+value);
        //释放连接
        connect.close();
        redisClient.shutdown();
    }

}
