package cn.edu.njuit.api.controller;

import cn.edu.njuit.api.common.result.Result;
import cn.edu.njuit.api.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "登录服务")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    /**
     * 登出接口
     *
     * @return 统一返回结果
     */
    @PostMapping("/logout")
    @Operation(summary = "登出")
    public Result<Object> logout() {
        authService.logout();
        return Result.ok();
    }


    /**
     * 绑定手机号接口
     *
     * @param phone       电话
     * @param code        验证码
     * @param accessToken 访问令牌
     * @return 统一返回结果
     */
    @PostMapping("/bindPhone")
    @Operation(summary = "绑定手机号")
    public Result<String> bindPhone(@RequestParam("phone") String phone,
                                    @RequestParam("code") String code,
                                    @RequestHeader("Authorization") String accessToken) {
        authService.bindPhone(phone, code, accessToken);
        return Result.ok();
    }
}
