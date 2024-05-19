package cn.edu.njuit.api.common.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Redis配置类，配置RedisTemplate使用FastJson进行序列化
 */
@Configuration
public class RedisConfig {

    /**
     * 配置RedisTemplate的Bean
     *
     * @param factory Redis连接工厂
     * @return 配置好的RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // Key和HashKey使用String序列化
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());

        // Value和HashValue使用FastJson序列化
        FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<>(Object.class);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);

        // 设置Redis连接工厂
        template.setConnectionFactory(factory);

        return template;
    }

    /**
     * FastJsonRedisSerializer使用FastJson进行序列化和反序列化
     *
     * @param <T> 序列化的对象类型
     */
    static class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
        public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
        private final Class<T> clazz;

        /**
         * 构造方法
         *
         * @param clazz 序列化对象的类
         */
        public FastJsonRedisSerializer(Class<T> clazz) {
            super();
            this.clazz = clazz;
        }

        /**
         * 序列化方法，将对象序列化为字节数组
         *
         * @param t 需要序列化的对象
         * @return 序列化后的字节数组
         * @throws SerializationException 序列化异常
         */
        @Override
        public byte[] serialize(T t) throws SerializationException {
            if (t == null) {
                return new byte[0];
            }
            return JSON.toJSONString(t, JSONWriter.Feature.WriteClassName).getBytes(DEFAULT_CHARSET);
        }

        /**
         * 反序列化方法，将字节数组反序列化为对象
         *
         * @param bytes 需要反序列化的字节数组
         * @return 反序列化后的对象
         * @throws SerializationException 反序列化异常
         */
        @Override
        public T deserialize(byte[] bytes) throws SerializationException {
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            String str = new String(bytes, DEFAULT_CHARSET);
            return JSON.parseObject(str, clazz);
        }
    }
}
