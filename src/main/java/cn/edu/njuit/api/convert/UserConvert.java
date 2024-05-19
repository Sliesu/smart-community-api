package cn.edu.njuit.api.convert;

import cn.edu.njuit.api.model.dto.UserEditDTO;
import cn.edu.njuit.api.model.entity.User;
import cn.edu.njuit.api.model.vo.UserInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserConvert {
    // 创建 UserConvert 的一个实例
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    // 定义从 User 实体到 UserInfoVO 视图对象的映射方法
    UserInfoVO convert(User user);

    // 定义从 UserEditDTO 数据传输对象到 User 实体的映射方法
    User convert(UserEditDTO dto);
}