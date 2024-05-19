package cn.edu.njuit.api.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Cloopen短信服务的配置类。
 * @author DingYihang
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "message.sms.ccp")
public class CloopenConfig {
    /**Cloopen短信服务的服务器IP地址。*/
    private String serverIp;

    /**Cloopen短信服务的端口号*/
    private String port;

    /**Cloopen短信服务的账户SID*/
    private String accountSId;

    /**Cloopen短信服务的账户认证令牌。*/
    private String accountToken;

    /** Cloopen短信服务的应用ID。*/
    private String appId;

    /**Cloopen短信服务的模板ID。*/
    private String templateId;
}
