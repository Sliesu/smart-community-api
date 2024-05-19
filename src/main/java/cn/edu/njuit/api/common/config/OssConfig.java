package cn.edu.njuit.api.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

/**
 * 阿里云 OSS 配置类
 * @author DingYihang
 */
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
public class OssConfig {
    /**OSS 服务地址*/
    private String endpoint;

    /** 访问密钥 ID*/
    private String accessKeyId;

    /**访问密钥 Secret*/
    private String accessKeySecret;

    /**存储桶名称*/
    private String bucketName;
}
