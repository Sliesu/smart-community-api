package cn.edu.njuit.api.controller;

import cn.edu.njuit.api.common.result.Result;
import cn.edu.njuit.api.model.dto.WxLoginDTO;
import cn.edu.njuit.api.model.vo.UserLoginVO;
import cn.edu.njuit.api.service.AuthService;
import cn.edu.njuit.api.service.CommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author DingYihang
 */
@Tag(name = "基础服务")
@RestController
@RequestMapping("/common")
@AllArgsConstructor
public class CommonController {
    private final CommonService commonService;
    private final AuthService authService;

    @PostMapping(value = "/upload/img")
    @Operation(summary = "图片上传")
    public Result<String> upload(@RequestBody MultipartFile file) {
        return Result.ok(commonService.upload(file));
    }

    @PostMapping("/sendSms")
    @Operation(summary = "发送短信")
    public Result<Object> sendSms(@RequestParam("phone") String phone) {
        commonService.sendSms(phone);
        return Result.ok();
    }

    @PostMapping("/login")
    @Operation(summary = "手机号验证码登录")
    public Result<UserLoginVO> loginByPhone(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        return Result.ok(authService.loginByPhone(phone, code));
    }

    @PostMapping("/weChatLogin")
    @Operation(summary = "微信登录")
    public Result<UserLoginVO> weChatLogin(@RequestBody WxLoginDTO dto) {
        return Result.ok(authService.weChatLogin(dto));
    }

}
