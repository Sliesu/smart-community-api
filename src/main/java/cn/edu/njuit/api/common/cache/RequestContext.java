package cn.edu.njuit.api.common.cache;

import cn.edu.njuit.api.common.constant.Constant;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求上下文管理类，用于管理线程相关的资源。
 */
public class RequestContext {

    // 用于存储线程相关资源的 ThreadLocal 对象
    private static final ThreadLocal<Map<Object, Object>> RESOURCES = new InheritableThreadLocalMap<>();

    /**
     * 将指定键值对放入线程资源中。
     *
     * @param key   键
     * @param value 值
     */
    public static void put(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key 不能为空");
        }
        if (value == null) {
            RESOURCES.get().remove(key);
            return;
        }
        RESOURCES.get().put(key, value);
    }

    /**
     * 获取当前用户的用户ID。
     *
     * @return 用户ID
     * @throws IllegalArgumentException 如果用户ID为null
     */
    public static Integer getUserId() {
        Object result = get(Constant.USER_ID);
        if (result == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        return Integer.valueOf(String.valueOf(result));
    }

    // 根据键获取对应的值
    private static Object get(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key 不能为空");
        }
        return RESOURCES.get().get(key);
    }

    /**
     * 清除线程资源。
     */
    public static void clear() {
        RESOURCES.remove();
    }

    // 用于存储线程资源的 InheritableThreadLocal 子类
    private static final class InheritableThreadLocalMap<T extends Map<Object, Object>> extends InheritableThreadLocal<Map<Object, Object>> {

        @Override
        protected Map<Object, Object> initialValue() {
            return new HashMap<>();
        }

        @Override
        protected Map<Object, Object> childValue(Map<Object, Object> parentValue) {
            if (parentValue != null) {
                return (Map<Object, Object>) ((HashMap<Object, Object>) parentValue).clone();
            } else {
                return null;
            }
        }
    }
}

