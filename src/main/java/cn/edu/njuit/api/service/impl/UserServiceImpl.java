package cn.edu.njuit.api.service.impl;

import cn.edu.njuit.api.common.cache.RequestContext;
import cn.edu.njuit.api.common.exception.ErrorCode;
import cn.edu.njuit.api.common.exception.ServerException;
import cn.edu.njuit.api.convert.UserConvert;
import cn.edu.njuit.api.mapper.UserMapper;
import cn.edu.njuit.api.model.dto.UserEditDTO;
import cn.edu.njuit.api.model.entity.User;
import cn.edu.njuit.api.model.vo.UserInfoVO;
import cn.edu.njuit.api.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public UserInfoVO userInfo() {
        // 查询数据库
        Integer userId = RequestContext.getUserId();
        User user = baseMapper.selectById(userId);
        if (user == null) {
            log.error("用户不存在, userId: {}", userId);
            throw new ServerException(ErrorCode.USER_NOT_EXIST);
        }
        //类型转换
        UserInfoVO userInfoVO = UserConvert.INSTANCE.convert(user);

        // TODO: 用户是否签到的逻辑
        return userInfoVO;
    }

    @Override
    public UserInfoVO updateInfo(UserEditDTO userEditDTO) {
        // 从上下文获取当前用户ID
        Integer userId = RequestContext.getUserId();
        // 设置DTO中的用户ID
        userEditDTO.setPkId(userId);

        // 将DTO转换为User实体
        User user = UserConvert.INSTANCE.convert(userEditDTO);

        if (user.getPkId() == null) {
            // 参数异常处理
            throw new ServerException(ErrorCode.PARAMS_ERROR);
        }

        try {
            // 进行更新操作
            if (baseMapper.updateById(user) < 1) {
                // 更新失败处理
                throw new ServerException("修改失败");
            }
        } catch (Exception e) {
            // 异常捕获处理
            throw new ServerException(e.getMessage());
        }

        // 返回更新后的用户信息
        return this.userInfo();
    }


}