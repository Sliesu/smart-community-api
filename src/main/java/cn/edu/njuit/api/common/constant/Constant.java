package cn.edu.njuit.api.common.constant;

/**
 * 常量接口
 * @author dingyihang
 */
public interface Constant {
    /** 创建时间 */
    String CREATE_TIME = "createTime";

    /** 更新时间 */
    String UPDATE_TIME = "updateTime";

    /** 逻辑删除标志 */
    String DELETE_FLAG = "deleteFlag";

    /** 用户ID */
    String USER_ID = "userId";

    /** 微信小程序AppID */
    String APP_ID = "你的appId";

    /** 微信小程序AppSecret */
    String APP_SECRET = "你的appSecret";

    /** 微信返回参数中的错误码属性名 */
    String WX_ERR_CODE = "errcode";

    /** 微信返回参数中的OpenID属性名 */
    String WX_OPENID = "openid";

    /** 微信返回参数中的Session Key属性名 */
    String WX_SESSION_KEY = "session_key";

    /** 前端未登录时携带的token */
    String NO_TOKEN = "no-token";
}