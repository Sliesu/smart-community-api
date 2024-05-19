package cn.edu.njuit.api.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 公共服务接口
 * @author DingYihang
 */
public interface CommonService {
    /**
     * 发送短信
     *
     * @param phone 手机号
     */
    void sendSms(String phone);

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 上传后的 URL
     */
    String upload(MultipartFile file);
}
