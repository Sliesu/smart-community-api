package cn.edu.njuit.api.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * @author DingYihang
 */
@Data
@TableName("t_user")
public class User {
    /**主键ID*/
    @TableId(value = "pk_id", type = IdType.AUTO)
    private Integer pkId;

    /**手机号*/
    private String phone;

    /**微信OpenID*/
    private String wxOpenId;

    /**头像*/
    private String avatar;

    /**昵称*/
    private String nickname;

    /**性别*/
    private Integer gender;

    /**生日*/
    private String birthday;

    /**积分*/
    private Integer bonus;

    /**备注*/
    private String remark;

    /**状态 (参见 AccountStatusEnum)
     * @see cn.edu.njuit.api.enums.AccountStatusEnum
     */
    private Integer enabled;
    /**逻辑删除标志*/
    @TableField(value = "delete_flag", fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleteFlag;

    /**更新时间*/
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**创建时间*/
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
