package com.hua.furnitureManagement.controller.admin;

import com.hua.furnitureManagement.common.constant.JwtClaimsConstant;
import com.hua.furnitureManagement.common.properties.JwtProperties;
import com.hua.furnitureManagement.common.result.Result;
import com.hua.furnitureManagement.common.util.JwtUtil;
import com.hua.furnitureManagement.pojo.dto.UserDTO;
import com.hua.furnitureManagement.pojo.vo.AdminVO;
import com.hua.furnitureManagement.pojo.vo.UserVO;
import com.hua.furnitureManagement.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping(value = "/login", name = "管理员登录")
    public Result<AdminVO> login(@RequestBody UserDTO user) {
        try {
            AdminVO login = adminService.login(user);

            // 生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.ADMIN_ID, login.getId());
            claims.put(JwtClaimsConstant.ROLE, "管理员");
            String token = JwtUtil.createJWT(
                    jwtProperties.getAdminSecretKey(),
                    jwtProperties.getAdminTtl(),
                    claims);

            login.setToken(token);
            return Result.success(login);
        } catch (Exception e) {
            log.error("登录失败, {}",e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}
