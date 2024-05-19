package cn.edu.njuit.api.common.interceptor;

import cn.edu.njuit.api.common.cache.RequestContext;
import cn.edu.njuit.api.common.cache.TokenStoreCache;
import cn.edu.njuit.api.common.constant.Constant;
import cn.edu.njuit.api.common.exception.ErrorCode;
import cn.edu.njuit.api.common.exception.ServerException;
import cn.edu.njuit.api.model.vo.UserLoginVO;
import cn.edu.njuit.api.service.AuthService;
import cn.edu.njuit.api.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@AllArgsConstructor
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenStoreCache tokenStoreCache;
    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取token
        String accessToken = JwtUtil.getAccessToken(request);
        if (StringUtils.isBlank(accessToken)) {
            throw new ServerException(ErrorCode.UNAUTHORIZED);
        }

        // 校验token
        if (!JwtUtil.validate(accessToken)) {
            throw new ServerException(ErrorCode.UNAUTHORIZED);
        }

        // 验证用户登录状态是否正常
        UserLoginVO user = tokenStoreCache.getUser(accessToken);
        if (ObjectUtils.isEmpty(user)) {
            throw new ServerException(ErrorCode.LOGIN_STATUS_EXPIRE);
        }

        // 验证用户是否可用
        boolean enabledFlag = authService.checkUserEnabled(user.getPkId());
        if (!enabledFlag) {
            throw new ServerException(ErrorCode.ACCOUNT_DISABLED);
        }

        // 保存用户id到上下文
        RequestContext.put(Constant.USER_ID, user.getPkId());
        return true;
    }
}
