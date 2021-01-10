package io.mlc.cache.cache.sentinel;

import org.springframework.util.NumberUtils;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

public class SentinelDemo {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 26379);
        //获取master节点信息
        List<Map<String, String>> maps = jedis.sentinelMasters();
        for (Map<String, String> map : maps) {
            Jedis client = new Jedis(map.get("ip"), NumberUtils.parseNumber(map.get("port"), Integer.class));
            client.set(map.get("ip"), map.get("port"));
            client.close();
        }
        //获取slave节点信息
        List<Map<String, String>> slaves = jedis.sentinelSlaves("master");
        for (Map<String, String> map : slaves) {
            Jedis client = new Jedis(map.get("ip"), NumberUtils.parseNumber(map.get("port"), Integer.class));
            String result = client.get("127.0.0.1");
            System.out.println("结果:" + result);
            client.close();
        }
        jedis.close();
    }
}
