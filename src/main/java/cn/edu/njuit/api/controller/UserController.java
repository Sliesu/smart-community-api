package cn.edu.njuit.api.controller;

import cn.edu.njuit.api.common.result.Result;
import cn.edu.njuit.api.model.dto.UserEditDTO;
import cn.edu.njuit.api.model.vo.UserInfoVO;
import cn.edu.njuit.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;


@Slf4j
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Tag(name = "用户接口")
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    @Operation(summary = "查询用户信息")
    public Result<UserInfoVO> userInfo() {
        return Result.ok(userService.userInfo());
    }

    @PostMapping("/update")
    @Operation(summary = "修改用户信息")
    public Result<UserInfoVO> update(@RequestBody UserEditDTO userEditDTO) {
        return Result.ok(userService.updateInfo(userEditDTO));
    }
}