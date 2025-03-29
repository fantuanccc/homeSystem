package com.hua.furnitureManagement.controller;

import com.hua.furnitureManagement.common.result.Response;
import com.hua.furnitureManagement.common.result.Result;
import com.hua.furnitureManagement.pojo.dto.UserDTO;
import com.hua.furnitureManagement.service.UserService;
import com.hua.furnitureManagement.pojo.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/furniture/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody UserDTO user) {
        try {
            return Response.success(userService.login(user));
        } catch (Exception e) {
            log.error("登录失败, {}",e.getMessage(), e);
            return Response.error(e.getMessage());
        }
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody UserDTO user) {
        try {
            userService.register(user);
            return Response.success("注册成功");
        } catch (Exception e) {
            log.error("注册失败, {}", e.getMessage(), e);
            return Response.error(e.getMessage());
        }
    }
}
