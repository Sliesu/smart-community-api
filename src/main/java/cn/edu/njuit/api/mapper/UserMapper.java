package cn.edu.njuit.api.mapper;

import cn.edu.njuit.api.model.entity.User;
import cn.edu.njuit.api.model.vo.UserLoginVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户数据访问接口，继承自BaseMapper。
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据手机号查询用户。
     *
     * @param phone 手机号
     * @return 用户对象
     */
    default User getByPhone(String phone) {
        return this.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone, phone));
    }

    /**
     * 根据微信OpenId查询用户。
     *
     * @param openId 微信OpenId
     * @return 用户对象
     */
    default User getByWxOpenId(String openId) {
        return this.selectOne(new LambdaQueryWrapper<User>().eq(User::getWxOpenId, openId));
    }

    /**
     * 手机号验证码登录。
     *
     * @param phone 手机号
     * @param code  验证码
     * @return 用户登录信息
     */
    UserLoginVO loginByPhone(String phone, String code);
}
