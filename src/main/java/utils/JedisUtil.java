package utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @className: JedisUtil
 * @description: redis工具栏
 * @author: Aim
 * @date: 2023/4/3
 **/
public class JedisUtil {

    public static Jedis getConnection() {
        try {
            URI redisURI = getRedisURI();
            return new Jedis(redisURI);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static JedisPool getPool() {
        try {
            URI redisURI = getRedisURI();
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxIdle(5);
            poolConfig.setMinIdle(1);
            poolConfig.setTestOnBorrow(true);
            poolConfig.setTestOnReturn(true);
            poolConfig.setTestWhileIdle(true);
            return new JedisPool(poolConfig, redisURI);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static URI getRedisURI() throws URISyntaxException {
        String redisUrl = System.getenv("REDIS_URL");
        if (redisUrl == null) {
            redisUrl = "http://localhost:6379";
        }
        return new URI(redisUrl);
    }
}
