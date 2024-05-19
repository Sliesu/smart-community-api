package cn.edu.njuit.api.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录视图对象
 */
@Data
@Schema(description = "用户登录VO")
public class UserLoginVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8212240698099812005L;

    @Schema(description = "用户ID")
    private Integer pkId;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "微信OpenId")
    private String wxOpenId;

    @Schema(description = "令牌")
    private String accessToken;
}
