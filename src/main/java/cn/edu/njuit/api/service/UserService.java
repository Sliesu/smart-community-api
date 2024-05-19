package cn.edu.njuit.api.service;


import cn.edu.njuit.api.model.dto.UserEditDTO;
import cn.edu.njuit.api.model.entity.User;
import cn.edu.njuit.api.model.vo.UserInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {

    /**
     * 用户信息
     *
     * @return {@link UserInfoVO}
     */
    UserInfoVO userInfo();

    /**
     * 更新信息
     * @param userEditDTO 用户编辑 DTO
     * @return UserInfoVO
     * */
    UserInfoVO updateInfo(UserEditDTO userEditDTO);

}
