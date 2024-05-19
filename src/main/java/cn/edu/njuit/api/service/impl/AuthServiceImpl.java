package cn.edu.njuit.api.service.impl;

import cn.edu.njuit.api.common.cache.RedisCache;
import cn.edu.njuit.api.common.cache.RedisKeys;
import cn.edu.njuit.api.common.cache.RequestContext;
import cn.edu.njuit.api.common.cache.TokenStoreCache;
import cn.edu.njuit.api.common.exception.ErrorCode;
import cn.edu.njuit.api.common.exception.ServerException;
import cn.edu.njuit.api.enums.AccountStatusEnum;
import cn.edu.njuit.api.mapper.UserMapper;
import cn.edu.njuit.api.model.dto.WxLoginDTO;
import cn.edu.njuit.api.model.entity.User;
import cn.edu.njuit.api.model.vo.UserLoginVO;
import cn.edu.njuit.api.service.AuthService;
import cn.edu.njuit.api.utils.AESUtil;
import cn.edu.njuit.api.utils.CommonUtils;
import cn.edu.njuit.api.utils.JwtUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static cn.edu.njuit.api.common.constant.Constant.*;

/**
 * 认证服务实现类，继承 MyBatis Plus 提供的 ServiceImpl 泛型类，并实现 AuthService 接口
 * @author DingYihang
 */
@Slf4j
@Service
public class AuthServiceImpl extends ServiceImpl<UserMapper, User> implements AuthService {

    @Resource
    private UserMapper userMapper;

    private final RedisCache redisCache;
    private final TokenStoreCache tokenStoreCache;

    public AuthServiceImpl(RedisCache redisCache, TokenStoreCache tokenStoreCache) {
        this.redisCache = redisCache;
        this.tokenStoreCache = tokenStoreCache;
    }

    @Override
    public UserLoginVO loginByPhone(String phone, String code) {
        // 获取验证码cacheKey
        String smsCacheKey = RedisKeys.getSmsKey(phone);
        // 从redis中获取验证码
        Integer redisCode = (Integer) redisCache.get(smsCacheKey);
        // 校验验证码合法性
        if (ObjectUtils.isEmpty(redisCode) || !redisCode.toString().equals(code)) {
            throw new ServerException(ErrorCode.SMS_CODE_ERROR);
        }
        // 删除用过的验证码
        redisCache.delete(smsCacheKey);
        // 根据手机号获取用户
        User user = baseMapper.getByPhone(phone);
        // 判断用户是否注册过，如果user为空代表未注册，进行注册。否则开启登录流程
        if (ObjectUtils.isEmpty(user)) {
            log.info("用户不存在，创建用户, phone: {}", phone);
            user = new User();
            user.setNickname(phone);
            user.setPhone(phone);
            user.setAvatar("默认头像的url");
            user.setEnabled(AccountStatusEnum.ENABLED.getValue());
            user.setBonus(0);
            user.setRemark("这个人很懒，什么都没有写");
            baseMapper.insert(user);
        }
        // 用户被禁用
        if (!user.getEnabled().equals(AccountStatusEnum.ENABLED.getValue())) {
            throw new ServerException(ErrorCode.ACCOUNT_DISABLED);
        }
        // 构造token
        String accessToken = JwtUtil.createToken(user.getPkId());

        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setPkId(user.getPkId());
        userLoginVO.setPhone(user.getPhone());
        userLoginVO.setWxOpenId(user.getWxOpenId());
        userLoginVO.setAccessToken(accessToken);
        tokenStoreCache.saveUser(accessToken, userLoginVO);
        return userLoginVO;
    }

    @Override
    public UserLoginVO weChatLogin(WxLoginDTO loginDTO) {
        // 构建请求URL
        String url = "https://api.weixin.qq.com/sns/jscode2session?"
                + "appid=" + APP_ID
                + "&secret=" + APP_SECRET
                + "&js_code=" + loginDTO.getCode()
                + "&grant_type=authorization_code";

        // 发起HTTP请求获取微信返回的数据
        RestTemplate restTemplate = new RestTemplate();
        String jsonData = restTemplate.getForObject(url, String.class);

        // 检查返回数据是否包含错误码
        if (StringUtils.contains(jsonData, WX_ERR_CODE)) {
            throw new ServerException("openId获取失败, " + jsonData);
        }

        // 解析返回数据
        JSONObject jsonObject = JSON.parseObject(jsonData);
        log.info("wxData: {}", jsonData);
        String openid = Objects.requireNonNull(jsonObject).getString(WX_OPENID);
        String sessionKey = jsonObject.getString(WX_SESSION_KEY);

        // 对用户加密数据解密
        String jsonUserData = AESUtil.decrypt(loginDTO.getEncryptedData(), sessionKey, loginDTO.getIv());
        log.info("wxUserInfo: {}", jsonUserData);
        JSONObject wxUserData = JSON.parseObject(jsonUserData);

        // 根据微信openid查询用户信息
        User user = baseMapper.getByWxOpenId(openid);

        // 如果用户不存在，则创建新用户
        if (ObjectUtils.isEmpty(user)) {
            log.info("用户不存在，创建用户, openId: {}", openid);
            user = new User();
            user.setWxOpenId(openid);
            user.setNickname(wxUserData.getString("nickName"));
            user.setAvatar(wxUserData.getString("avatarUrl"));
            user.setGender(wxUserData.getInteger("gender"));
            user.setEnabled(AccountStatusEnum.ENABLED.getValue());
            user.setBonus(0);
            user.setRemark("这个人很懒，什么都没有写");
            baseMapper.insert(user);
        }

        // 检查用户是否被禁用
        if (!user.getEnabled().equals(AccountStatusEnum.ENABLED.getValue())) {
            throw new ServerException(ErrorCode.ACCOUNT_DISABLED);
        }

        // 生成访问令牌并保存用户信息到缓存
        String accessToken = JwtUtil.createToken(user.getPkId());
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setPkId(user.getPkId());
        if (StringUtils.isNotBlank(user.getPhone())) {
            userLoginVO.setPhone(user.getPhone());
        }
        userLoginVO.setWxOpenId(user.getWxOpenId());
        userLoginVO.setAccessToken(accessToken);
        tokenStoreCache.saveUser(accessToken, userLoginVO);

        return userLoginVO;
    }

    /**
     * 检查用户是否启用。
     *
     * @param userId 用户 ID
     * @return 如果用户启用，返回 true，否则返回 false
     */
    @Override
    public boolean checkUserEnabled(Integer userId) {
        User user = userMapper.selectById(userId);
        if (ObjectUtils.isEmpty(user)) {
            return false;
        }
        return user.getEnabled().equals(AccountStatusEnum.ENABLED.getValue());
    }

    /**
     * 用户登出。
     */
    @Override
    public void logout() {
        // 从上下文中获取 userId，然后获取 redisKey
        String cacheKey = RedisKeys.getUserIdKey(RequestContext.getUserId());

        // 通过 userId，获取 redis 中的 accessToken
        String accessToken = (String) redisCache.get(cacheKey);

        // 删除缓存中的 token
        redisCache.delete(cacheKey);

        // 删除缓存中的用户信息
        tokenStoreCache.deleteUser(accessToken);
    }

    /**
     * 绑定手机号
     *
     * @param phone       电话
     * @param code        验证码
     * @param accessToken 访问令牌
     */
    @Override
    public void bindPhone(String phone, String code, String accessToken) {
        // 简单校验手机号合法性
        if (!CommonUtils.checkPhone(phone)) {
            throw new ServerException(ErrorCode.PARAMS_ERROR);
        }

        // 获取手机验证码，校验验证码正确性
        String redisCode = (String) redisCache.get(RedisKeys.getSmsKey(phone));
        if (ObjectUtils.isEmpty(redisCode) || !redisCode.equals(code)) {
            throw new ServerException(ErrorCode.SMS_CODE_ERROR);
        }

        // 删除验证码缓存
        redisCache.delete(RedisKeys.getSmsKey(phone));

        // 获取当前用户信息
        User userByPhone = userMapper.getByPhone(phone);

        // 获取当前登录的用户信息
        UserLoginVO userLogin = tokenStoreCache.getUser(accessToken);

        // 判断新手机号是否存在用户
        if (ObjectUtils.isEmpty(userByPhone)) {
            // 存在用户，并且不是当前用户，抛出异常
            if (!userLogin.getPkId().equals(userByPhone.getPkId())) {
                throw new ServerException(ErrorCode.PHONE_IS_EXIST);
            }

            // 存在用户，并且是当前用户，提示用户手机号相同
            if (userLogin.getPhone().equals(phone)) {
                throw new ServerException(ErrorCode.THE_SAME_PHONE);
            }
        }

        // 重新设置手机号
        User user = userMapper.selectById(userLogin.getPkId());
        user.setPhone(phone);
        if (userMapper.updateById(user) < 1) {
            throw new ServerException(ErrorCode.OPERATION_FAIL);
        }
    }


}