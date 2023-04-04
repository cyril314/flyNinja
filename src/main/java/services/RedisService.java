package services;

import com.google.inject.Inject;
import ninja.Context;
import org.slf4j.Logger;
import redis.clients.jedis.Jedis;
import utils.JacksonUtil;
import utils.JedisUtil;

import java.util.Map;

/**
 * @className: RedisService
 * @description:
 * @author: Aim
 * @date: 2023/4/3
 **/
public class RedisService {

    @Inject
    Logger log;

    private Jedis jedis = null;

    public <T> T getEntity(String key, Class<T> clazz) {
        String jsonParam = JacksonUtil.toJSon(getValue(key));
        return JacksonUtil.readValue(jsonParam, clazz);
    }

    public void setEntity(Map<String, Object> params, String key) {
        String jsonParam = JacksonUtil.toJSon(params);
        setValue(key, jsonParam);
    }

    public void setValue(String key, String value) {
        try {
            getJedis();
            log.info("Set key [{}] value [{}] into redis", key, value);
            jedis.set(key, value);
        } finally {
            toClose();
        }
    }

    public String getValue(String key) {
        try {
            getJedis();
            String value = jedis.get(key);
            log.info("Getting key [{}] with value [{}] from redis", key, value);
            return jedis.get(key);
        } finally {
            toClose();
        }
    }

    public void clear(String key) {
        try {
            getJedis();
            log.info("Deleting key [{}] from Redis store", key);
            jedis.del(key);
        } finally {
            toClose();
        }
    }

    private void getJedis() {
        if (jedis == null) {
            jedis = JedisUtil.getPool().getResource();
        }
    }

    private void toClose() {
        if (jedis != null) {
            jedis.close();
        }
    }
}
