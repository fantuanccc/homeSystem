package com.hua.furnitureManagement.controller.user;

import com.hua.furnitureManagement.common.constant.JwtClaimsConstant;
import com.hua.furnitureManagement.common.context.BaseContext;
import com.hua.furnitureManagement.common.properties.JwtProperties;
import com.hua.furnitureManagement.common.result.Result;
import com.hua.furnitureManagement.common.util.JwtUtil;
import com.hua.furnitureManagement.pojo.dto.UserDTO;
import com.hua.furnitureManagement.pojo.vo.UserDetailVO;
import com.hua.furnitureManagement.service.UserService;
import com.hua.furnitureManagement.pojo.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户模块
 * @Author hua
 * @Date 2025/3/30
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping(value = "/login", name = "登录")
    public Result<UserVO> login(@RequestBody UserDTO user) {
        try {
            UserVO login = userService.login(user);

            // 生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.USER_ID, login.getId());
            claims.put(JwtClaimsConstant.ROLE, login.getIsOwner() == 1 ? "户主" : "家庭成员");
            claims.put(JwtClaimsConstant.ADDRESS_ID, login.getAddressId());
            String token = JwtUtil.createJWT(
                    jwtProperties.getUserSecretKey(),
                    jwtProperties.getUserTtl(),
                    claims);

            login.setToken(token);
            return Result.success(login);
        } catch (Exception e) {
            log.error("登录失败, {}",e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @PostMapping(value = "/register", name = "用户注册")
    public Result<String> register(@RequestBody UserDTO user) {
        try {
            userService.register(user);
            return Result.success("注册成功");
        } catch (Exception e) {
            log.error("注册失败, {}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping(value = "/info", name = "获取用户信息")
    public Result<UserDetailVO> getUserInfo() {
        try {
            // 获取当前登录用户id和住址id
            Long userId = BaseContext.getCurrentId();
            Long addressId = BaseContext.getCurrentAddressId();
            return Result.success(userService.getUserInfo(userId, addressId));
        } catch (Exception e) {
            log.error("获取用户信息失败, {}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @PostMapping(value = "/edit", name = "编辑用户信息")
    public Result<String> edit(@RequestBody UserDTO user) {
        try {
            userService.edit(user);
            return Result.success("注册成功");
        } catch (Exception e) {
            log.error("注册失败, {}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }


}
