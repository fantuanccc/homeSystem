package com.hua.furnitureManagement.controller.user;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.hua.furnitureManagement.common.constant.JwtClaimsConstant;
import com.hua.furnitureManagement.common.context.BaseContext;
import com.hua.furnitureManagement.common.properties.JwtProperties;
import com.hua.furnitureManagement.common.result.Result;
import com.hua.furnitureManagement.common.util.JwtUtil;
import com.hua.furnitureManagement.pojo.dto.AddressDTO;
import com.hua.furnitureManagement.pojo.dto.UserDTO;
import com.hua.furnitureManagement.pojo.vo.UserDetailVO;
import com.hua.furnitureManagement.service.SendSms;
import com.hua.furnitureManagement.service.UserService;
import com.hua.furnitureManagement.pojo.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户模块
 *
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
    @Autowired
    private SendSms sendSms;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping(value = "/login", name = "密码登录")
    public Result<UserVO> login(@RequestBody UserDTO request) {
        try {
            // 判断用户登录是否成功
            UserVO login = userService.login(request);

            // 获取所有地址信息，当用户存在多个房子，取最早添加的房子作为默认地址
            List<Map<String, Object>> addressList = userService.userAllAddress(login.getId());
            Long addressId = (Long) addressList.get(0).get("addressId");
            Integer role = userService.getRole(login.getId(), addressId);
            // 生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.USER_ID, login.getId());
            claims.put(JwtClaimsConstant.ROLE, role == 1 ? "户主" : "家庭成员");
            claims.put(JwtClaimsConstant.ADDRESS_ID, addressId);
            String token = JwtUtil.createJWT(
                    jwtProperties.getUserSecretKey(),
                    jwtProperties.getUserTtl(),
                    claims);
            login.setIsOwner(role);
            login.setToken(token);
            log.info("{} 登录成功, token: {}", login.getId(), token);
            return Result.success(login);
        } catch (Exception e) {
            log.error("登录失败, {}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @PostMapping(value = "/codeLogin", name = "验证码登录")
    public Result<UserVO> codeLogin(@RequestBody UserDTO request) {
        try {
            UserVO login = new UserVO();
            // 判断手机号号是否注册
            if(!userService.isExistPhoneNumber(request.getPhoneNumber())){
                log.error("手机号码未注册!");
                return Result.error("手机号码未注册！");
            }
            // 判断验证码是否正确
            String code = (String) redisTemplate.opsForValue().get("login_" + request.getPhoneNumber());
            if(StrUtil.isBlank(code)){
                log.error("验证码已过期!");
                return Result.error("验证码已过期！");
            }
            if(!code.equals(request.getCode())){
                log.error("验证码错误!");
                return Result.error("验证码错误！");
            }
            // 根据手机号获取用户id
            Long userId = userService.getUserId(request.getPhoneNumber());
            // 根据用户id获取住址信息   获取所有地址信息，当用户存在多个房子，取最早添加的房子作为默认地址
            List<Map<String, Object>> addressList = userService.userAllAddress(userId);
            Long addressId = (Long) addressList.get(0).get("addressId");
            Integer role = userService.getRole(userId, addressId);
            // 生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.USER_ID, userId);
            claims.put(JwtClaimsConstant.ROLE, role == 1 ? "户主" : "家庭成员");
            claims.put(JwtClaimsConstant.ADDRESS_ID, addressId);
            String token = JwtUtil.createJWT(
                    jwtProperties.getUserSecretKey(),
                    jwtProperties.getUserTtl(),
                    claims);
            login.setId(userId);
            login.setIsOwner(role);
            login.setToken(token);
            log.info("{} 登录成功, token: {}", userId, token);
            return Result.success(login);
        } catch (Exception e) {
            log.error("登录失败, {}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @PostMapping(value = "/register", name = "用户注册")
    public Result<String> register(@RequestBody UserDTO user) {
        try {
            // 判断验证码是否正确
            String code = (String) redisTemplate.opsForValue().get("register_" + user.getPhoneNumber());
            if(StrUtil.isBlank(code)){
                log.error("验证码已过期!");
                return Result.error("验证码已过期！");
            }
            if(!code.equals(user.getCode())){
                log.error("验证码错误!");
                return Result.error("验证码错误！");
            }
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
            return Result.success("更新信息成功");
        } catch (Exception e) {
            log.error("更新信息失败, {}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping(value = "/applyKey", name = "申请密钥")
    public Result<String> applyKey() {
        try {
            userService.applyKey();
            return Result.success("密钥申请成功");
        } catch (Exception e) {
            log.error("密钥申请失败, {}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping(value = "/updateKeyStatus", name = "启用禁用密钥")
    public Result<String> updateKeyStatus(@RequestParam Integer status) {
        try {
            userService.updateKeyStatus(status);
            return Result.success("密钥状态修改成功");
        } catch (Exception e) {
            log.error("密钥状态修改失败, 原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @PostMapping(value = "/addAddress", name = "添加用户住址信息")
    public Result<String> addAddress(@RequestBody AddressDTO request) {
        try {
            Long addressId = userService.addAddress(request);

            // 生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.USER_ID, BaseContext.getCurrentId());
            claims.put(JwtClaimsConstant.ROLE, BaseContext.getCurrentRole());
            claims.put(JwtClaimsConstant.ADDRESS_ID, addressId);
            String token = JwtUtil.createJWT(
                    jwtProperties.getUserSecretKey(),
                    jwtProperties.getUserTtl(),
                    claims);
            return Result.success(token);
        } catch (Exception e) {
            log.error("添加地址信息失败, 原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping(value = "/allAddressName", name = "获取所有小区名字，用于下拉框选择")
    public Result<List<String>> allAddressName() {
        try {
            return Result.success(userService.allAddressName());
        } catch (Exception e) {
            log.error("获取所有小区名字, 原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping(value = "/unitNumber", name = "获取对应小区下面的单元号，用于下拉框选择")
    public Result<List<String>> unitNumber(@RequestParam String addressName) {
        try {
            return Result.success(userService.unitNumber(addressName));
        } catch (Exception e) {
            log.error("获取对应小区下面的单元号, 原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping(value = "/userAllAddress", name = "获取当前用户下的所有房子地址，用于下拉框选择")
    public Result<List<Map<String, Object>>> userAllAddress() {
        try {
            return Result.success(userService.userAllAddress(BaseContext.getCurrentId()));
        } catch (Exception e) {
            log.error("获取当前用户下的所有房子地址, 原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping(value = "/switchAddress", name = "切换住址")
    public Result<String> switchAddress(@RequestParam Long addressId) {
        try {
            Integer role = userService.getRole(BaseContext.getCurrentId(), addressId);
            log.info("用户 {} 切换住址为 {}, 所属用户角色：{}", BaseContext.getCurrentId(), addressId, role);
            // 生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.USER_ID, BaseContext.getCurrentId());
            claims.put(JwtClaimsConstant.ROLE, role == 1 ? "户主" : "家庭成员");
            claims.put(JwtClaimsConstant.ADDRESS_ID, addressId);
            String token = JwtUtil.createJWT(
                    jwtProperties.getUserSecretKey(),
                    jwtProperties.getUserTtl(),
                    claims);
            return Result.success(token);
        } catch (Exception e) {
            log.error("切换住址, 原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping(value = "/sendLoginCode", name = "发送登录验证码")
    public Result<String> sendLoginCode(@RequestParam String phone) {
        try {
            //从redis中获取短信验证码
            String code = (String) redisTemplate.opsForValue().get("login_" + phone);
            //如果redis中有，则短信验证码已发送并且有效，直接返回验证码已发送
            if (StrUtil.isNotEmpty(code)) {
                log.info("{} 验证码已发送！验证码为：{}", phone, code);
                return Result.success("请勿重复发送验证码！");
            }
            //如果redis中没有查到验证码，直接生成6位数验证码，此code和短信模板中的变量${code}保持一致
            code = RandomUtil.randomNumbers(6);
            Map<String, Object> map = new HashMap<>(1);
            map.put("code", code);
            //发送验证码，参数中的模板CODE为阿里云模板管理中生成的模板CODE
            Boolean isSend = sendSms.send(phone, map);
            //发送成功后将短信验证码存入redis中，设置有效期为5分钟
            if (isSend) {
                redisTemplate.opsForValue().set("login_" + phone, code, 5, TimeUnit.MINUTES);
                log.info("{} 验证码发送成功！验证码为：{}", phone, code);
                return Result.success("验证码发送成功！");
            } else {
                log.error("{} 验证码发送失败！", phone);
                return Result.error("验证码发送失败！");
            }
        } catch (Exception e) {
            log.error("发送验证码失败, 原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping(value = "/sendRegisterCode", name = "发送注册验证码")
    public Result<String> sendRegisterCode(@RequestParam String phone) {
        try {
            //从redis中获取短信验证码
            String code = (String) redisTemplate.opsForValue().get("register_" + phone);
            //如果redis中有，则短信验证码已发送并且有效，直接返回验证码已发送
            if (StrUtil.isNotEmpty(code)) {
                log.info("{} 验证码已发送！验证码为：{}", phone, code);
                return Result.success("请勿重复发送验证码！");
            }
            //如果redis中没有查到验证码，直接生成6位数验证码，此code和短信模板中的变量${code}保持一致
            code = RandomUtil.randomNumbers(6);
            Map<String, Object> map = new HashMap<>(1);
            map.put("code", code);
            //发送验证码，参数中的模板CODE为阿里云模板管理中生成的模板CODE
            Boolean isSend = sendSms.send(phone, map);
            //发送成功后将短信验证码存入redis中，设置有效期为5分钟
            if (isSend) {
                redisTemplate.opsForValue().set("register_" + phone, code, 5, TimeUnit.MINUTES);
                log.info("{} 验证码发送成功！验证码为：{}", phone, code);
                return Result.success("验证码发送成功！");
            } else {
                log.error("{} 验证码发送失败！", phone);
                return Result.error("验证码发送失败！");
            }
        } catch (Exception e) {
            log.error("发送验证码失败, 原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}
