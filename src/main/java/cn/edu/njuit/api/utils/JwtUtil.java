package cn.edu.njuit.api.utils;

import cn.edu.njuit.api.common.constant.Constant;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.RegisteredPayload;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类，用于生成和验证JWT token。
 * @author DingYihang
 */
public class JwtUtil {

    /**
     * 盐值，用于签名JWT token。
     */
    private static final String KEY = "app-demo";

    /**
     * 日志记录器
     */
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 创建JWT token。
     *
     * @param userId 用户ID
     * @return JWT token
     */
    public static String createToken(Integer userId) {
        DateTime now = DateTime.now();
        DateTime expTime = now.offsetNew(DateField.HOUR, 48);

        // 构建payload
        Map<String, Object> payload = new HashMap<>();
        payload.put(RegisteredPayload.ISSUED_AT, now);
        payload.put(RegisteredPayload.EXPIRES_AT, expTime);
        payload.put(RegisteredPayload.NOT_BEFORE, now);
        payload.put(Constant.USER_ID, userId);

        // 创建JWT token
        String token = JWTUtil.createToken(payload, KEY.getBytes());
        log.info("生成 JWT token：{}", token);
        return token;
    }

    /**
     * 验证JWT token是否有效。
     *
     * @param token JWT token
     * @return true表示token有效，false表示token无效
     */
    public static boolean validate(String token) {
        try {
            JWT jwt = JWTUtil.parseToken(token).setKey(KEY.getBytes());
            // validate方法包含了verify
            boolean validate = jwt.validate(0);
            log.info("JWT token 校验结果：{}", validate);
            return validate;
        } catch (Exception e) {
            log.error("JWT token 校验异常：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 根据JWT token获取JSON对象。
     *
     * @param token JWT token
     * @return JSON对象
     */
    public static JSONObject getJSONObject(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(KEY.getBytes());
        JSONObject payloads = jwt.getPayloads();
        payloads.remove(RegisteredPayload.ISSUED_AT);
        payloads.remove(RegisteredPayload.EXPIRES_AT);
        payloads.remove(RegisteredPayload.NOT_BEFORE);
        log.info("根据 token 获取原始内容：{}", payloads);
        return payloads;
    }

    /**
     * 从HttpServletRequest中获取访问令牌。
     *
     * @param request HTTP请求对象
     * @return 访问令牌
     */
    public static String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        if (StringUtils.isBlank(accessToken)) {
            accessToken = request.getParameter("accessToken");
        }
        return accessToken;
    }

    // public static void main(String[] args) {
    //     String token = createToken(10001L);
    //     System.out.println(token);
    //     validate(token);
    //     getJSONObject(token);
    // }
}
