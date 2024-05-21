package cn.edu.njuit.api.common.interceptor;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * PermitResource 类用于获取需要被检查的 URL 列表。
 */
@Component
public class PermitResource {

    private static final List<String> VALID_URLS = new ArrayList<>();

    static {
        // 在这里添加需要被校验的 URL
        VALID_URLS.add("/auth/logout");
        VALID_URLS.add("/user/**");
    }

    /**
     * 获取需要被校验的 URL 列表。
     *
     * @return 需要被校验的 URL 列表
     */
    public List<String> getValidList() {
        return VALID_URLS;
    }
}
