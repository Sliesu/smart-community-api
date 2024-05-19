package cn.edu.njuit.api.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Data
@Schema(description = "用户修改dto")
public class UserEditDTO {
    @Schema(description = "主键")
    private Integer pkId;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "生日")
    private String birthday;

    @Schema(description = "描述")
    private String remark;
}
