package cn.edu.njuit.api.common.cache;


import cn.edu.njuit.api.model.vo.UserLoginVO;
import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

import static cn.edu.njuit.api.common.cache.RedisCache.HOUR_SIX_EXPIRE;

/**
 * Token 存储和管理缓存
 * @author DingYihang
 */
@Component
@AllArgsConstructor
@Slf4j
public class TokenStoreCache {
    private final RedisCache redisCache;

    /**
     * 保存用户信息到缓存
     *
     * @param accessToken 用户访问令牌
     * @param user        用户信息
     */
    public void saveUser(String accessToken, UserLoginVO user) {
        String accessTokenKey = RedisKeys.getAccessTokenKey(accessToken);
        String userIdKey = RedisKeys.getUserIdKey(user.getPkId());

        // 删除已有的用户缓存
        if (redisCache.get(userIdKey) != null) {
            redisCache.delete(String.valueOf(redisCache.get(userIdKey)));
        }

        log.info("accessToken = {}", accessToken);
        redisCache.set(userIdKey, accessToken, HOUR_SIX_EXPIRE);
        redisCache.set(accessTokenKey, user, HOUR_SIX_EXPIRE);
    }

    /**
     * 从缓存中获取用户信息
     *
     * @param accessToken 用户访问令牌
     * @return 用户信息
     */
    public UserLoginVO getUser(String accessToken) {
        String key = RedisKeys.getAccessTokenKey(accessToken);
        return JSON.toJavaObject((JSON) redisCache.get(key), UserLoginVO.class);
    }

    /**
     * 从缓存中删除用户信息
     *
     * @param accessToken 用户访问令牌
     */
    public void deleteUser(String accessToken) {
        String key = RedisKeys.getAccessTokenKey(accessToken);
        redisCache.delete(key);
    }

    /**
     * 根据用户ID从缓存中删除用户信息
     *
     * @param id 用户ID
     */
    public void deleteUserById(Integer id) {
        String userId = RedisKeys.getUserIdKey(id);
        String key = String.valueOf(redisCache.get(userId));
        redisCache.delete(key);
    }

    /**
     * 根据多个用户ID从缓存中删除用户信息
     *
     * @param ids 用户ID列表
     */
    public void deleteUserByIds(List<Integer> ids) {
        List<String> keys = new ArrayList<>();
        for (Integer id : ids) {
            String userId = RedisKeys.getUserIdKey(id);
            String key = String.valueOf(redisCache.get(userId));
            keys.add(key);
        }
        redisCache.delete(keys.toString());
    }
}
