package cn.edu.njuit.api.service;

import cn.edu.njuit.api.model.dto.WxLoginDTO;
import cn.edu.njuit.api.model.entity.User;
import cn.edu.njuit.api.model.vo.UserLoginVO;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 认证服务接口，继承 MyBatis Plus 提供的 IService 泛型接口
 */
public interface AuthService extends IService<User> {
    /**
     * 手机号验证码登录。
     *
     * @param phone 手机号
     * @param code  验证码
     * @return 用户登录信息
     */
    UserLoginVO loginByPhone(String phone, String code);

    /**
     * 微信登录
     *
     * @param loginDTO DTO
     * @return {@link UserLoginVO}
     */
    UserLoginVO weChatLogin(WxLoginDTO loginDTO);

    /**
     * 检查用户是否启用。
     *
     * @param userId 用户 ID
     * @return 如果用户启用，返回 true，否则返回 false
     */
    boolean checkUserEnabled(Integer userId);

    /**
     * 用户退出登录。
     */
    void logout();

    /**
     * 绑定手机号
     *
     * @param phone       电话
     * @param code        验证码
     * @param accessToken 访问令牌
     */
    void bindPhone(String phone, String code, String accessToken);

}
