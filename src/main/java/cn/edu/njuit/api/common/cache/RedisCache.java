package cn.edu.njuit.api.common.cache;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存工具类，封装了对Redis的常用操作方法。
 */
@Component
public class RedisCache {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 默认过期时长为24小时，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24L;

    /**
     * 过期时长为1小时，单位：秒
     */
    public final static long HOUR_ONE_EXPIRE = 60 * 60;

    /**
     * 过期时长为6小时，单位：秒
     */
    public final static long HOUR_SIX_EXPIRE = 60 * 60 * 6L;

    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1L;

    /**
     * 设置缓存值，并指定过期时间。
     *
     * @param key    缓存键
     * @param value  缓存值
     * @param expire 过期时间（单位：秒），传入 {@link #NOT_EXPIRE} 表示不设置过期时间
     */
    public void set(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(key, value);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * 设置缓存值，默认过期时间为24小时。
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 获取缓存值，并指定过期时间。
     *
     * @param key    缓存键
     * @param expire 过期时间（单位：秒），传入 {@link #NOT_EXPIRE} 表示不设置过期时间
     * @return 缓存值
     */
    public Object get(String key, long expire) {
        Object value = redisTemplate.opsForValue().get(key);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
        return value;
    }

    /**
     * 获取缓存值。
     *
     * @param key 缓存键
     * @return 缓存值
     */
    public Object get(String key) {
        return get(key, NOT_EXPIRE);
    }


    /**
     * 设置缓存过期时间。
     *
     * @param key    缓存键
     * @param expire 过期时间（单位：秒）
     */
    public void expire(String key, long expire) {
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 删除指定键的缓存值。
     *
     * @param key 缓存键
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
